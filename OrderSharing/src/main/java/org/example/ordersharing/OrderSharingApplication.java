package org.example.ordersharing;

import org.example.ordersharing.controller.OrderController;
import org.example.ordersharing.model.IndividualOrder;
import org.example.ordersharing.model.SharedOrder;
import org.example.ordersharing.model.User;
import org.example.ordersharing.repository.IndividualOrderRepository;
import org.example.ordersharing.repository.SharedOrderRepository;
import org.example.ordersharing.repository.UserRepository;
import org.example.ordersharing.sender.Notification;
import org.example.ordersharing.utils.HttpError;
import org.example.ordersharing.utils.SharedOrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@SpringBootApplication
public class OrderSharingApplication {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SharedOrderRepository sharedOrderRepository;
    @Autowired
    IndividualOrderRepository individualOrderRepository;
    Logger logger = Logger.getLogger(getClass().getName());
    Notification notification = message -> logger.info(() -> "Notification: " + message);

    public static void main(String[] args) {
        SpringApplication.run(OrderSharingApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/get-users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get-orders")
    public List<IndividualOrder> listIndividualOrders() {
        return individualOrderRepository.findAll();
    }

    @GetMapping("/get-shared-orders")
    public List<SharedOrder> listSharedOrders() {
        return sharedOrderRepository.findAll();
    }

    @PostMapping("/get-orders-by-park")
    public List<SharedOrder> listOrdersByPark(@RequestParam(value = "idAgent", defaultValue = HttpError.NOT_SPECIFIED) String idAgent,
            @RequestParam(value = "parkName", defaultValue = HttpError.NOT_SPECIFIED) String parkName) {
        if (parkName.equals(HttpError.NOT_SPECIFIED) || idAgent.equals(HttpError.NOT_SPECIFIED)) {
            throw new IllegalArgumentException(HttpError.NOT_SPECIFIED);
        }
        User user = userRepository.findById(idAgent).orElseThrow(() -> new IllegalArgumentException(HttpError.NOT_FOUND));
        if(user == null || !user.getRole().equals("agent")) {
            throw new IllegalArgumentException(HttpError.NOT_FOUND);
        }
        return sharedOrderRepository.findByParkName(parkName);
    }

    @PostMapping("/pay-order-amount")
    public String payOrder(@RequestParam(value = "orderId", defaultValue = HttpError.NOT_SPECIFIED) String orderId,
                           @RequestParam(value = "amount", defaultValue = "0") String amount) {
        if (orderId.equals(HttpError.NOT_SPECIFIED)) {
            throw new IllegalArgumentException(HttpError.NOT_SPECIFIED);
        }
        double sum = Double.parseDouble(amount);
        if (sum <= 0) {
            throw new IllegalArgumentException(HttpError.ERROR_AMOUNT_SPECIFIED);
        }
        SharedOrder order = sharedOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException(HttpError.NOT_FOUND));
        if(order == null) {
            throw new IllegalArgumentException(HttpError.NOT_FOUND);
        }
        return OrderController.payOrder(order, sum, sharedOrderRepository, notification);
    }

    @PostMapping("/pay-full-order")
    public String payFullOrder(@RequestParam(value = "orderId", defaultValue = HttpError.NOT_SPECIFIED) String orderId) {
        if (orderId.equals(HttpError.NOT_SPECIFIED)) {
            throw new IllegalArgumentException(HttpError.NOT_SPECIFIED);
        }
        SharedOrder order = sharedOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException(HttpError.NOT_FOUND));
        if(order == null) {
            throw new IllegalArgumentException(HttpError.NOT_FOUND);
        }
        return OrderController.payOrder(order, order.getToPay(), sharedOrderRepository, notification);
    }

    @PostMapping("/pay-one-order")
    public String payOneOrder(@RequestParam(value = "orderId", defaultValue = HttpError.NOT_SPECIFIED) String orderId,
                              @RequestParam(value = "individualOrderId", defaultValue = HttpError.NOT_SPECIFIED) String individualOrderId) {
        if (orderId.equals(HttpError.NOT_SPECIFIED) || individualOrderId.equals(HttpError.NOT_SPECIFIED)) {
            throw new IllegalArgumentException(HttpError.NOT_SPECIFIED);
        }
        SharedOrder order = sharedOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException(HttpError.NOT_FOUND));
        if(order == null) {
            throw new IllegalArgumentException(HttpError.NOT_FOUND);
        }
        IndividualOrder individualOrder = SharedOrderHelper.getIndividualOrderFromSharedOrder(order, individualOrderId);
        if(individualOrder == null) {
            throw new IllegalArgumentException(HttpError.NOT_FOUND);
        }
        return OrderController.payOrder(order, individualOrder.getTotalPrice(), sharedOrderRepository, notification);
    }
}
