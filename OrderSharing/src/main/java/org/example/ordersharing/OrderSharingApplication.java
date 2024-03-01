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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class OrderSharingApplication {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SharedOrderRepository sharedOrderRepository;
    @Autowired
    IndividualOrderRepository individualOrderRepository;

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

    @GetMapping("/get-orders-by-park")
    public List<SharedOrder> listOrdersByPark(@RequestParam(value = "parkName", defaultValue = HttpError.NOT_SPECIFIED) String parkName) {
        if (parkName.equals(HttpError.NOT_SPECIFIED)) {
            throw new IllegalArgumentException(HttpError.NOT_SPECIFIED);
        }
        return sharedOrderRepository.findByParkName(parkName);
    }

    @PostMapping("/pay-order")
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
        Notification notification = message -> System.out.println("Notification: " + message);
        return OrderController.payOrder(order, sum, sharedOrderRepository, notification);
    }
}
