package org.example.ordersharing;

import org.example.ordersharing.controller.OrderController;
import org.example.ordersharing.model.IndividualOrder;
import org.example.ordersharing.model.SharedOrder;
import org.example.ordersharing.model.User;
import org.example.ordersharing.model.Product;
import org.example.ordersharing.repository.*;
import org.example.ordersharing.sender.Notification;
import org.example.ordersharing.utils.HttpError;
import org.example.ordersharing.utils.SharedOrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ParkRepository parkRepository;
    Logger logger = Logger.getLogger(getClass().getName());
    Notification notification = message -> logger.info(() -> "Notification: " + message);

    public static void main(String[] args) {
        SpringApplication.run(OrderSharingApplication.class, args);
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/orders")
    public List<IndividualOrder> listIndividualOrders() {
        return individualOrderRepository.findAll();
    }

    @GetMapping("/shared-orders")
    public List<SharedOrder> listSharedOrders() {
        return sharedOrderRepository.findAll();
    }

    @PostMapping("/orders-by-park")
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

    @GetMapping("/products")
    public List<Product> listProducts(@RequestParam(value = "parkName", defaultValue = HttpError.NOT_SPECIFIED) String parkName) {
        if(parkName.equals(HttpError.NOT_SPECIFIED))
            return productRepository.findAll();
        return productRepository.findByParkName(parkName);
    }

    @PostMapping("/pay-order-amount")
    public ResponseEntity<String> payOrder(@RequestParam(value = "orderId", defaultValue = HttpError.NOT_SPECIFIED) String orderId,
                           @RequestParam(value = "amount", defaultValue = "0") String amount) {
        if (orderId.equals(HttpError.NOT_SPECIFIED)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_SPECIFIED + ". You must have an orderId");
        }
        double sum = Double.parseDouble(amount);
        if (sum <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.ERROR_AMOUNT_SPECIFIED + " for amount");
        }
        SharedOrder order = sharedOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException(HttpError.NOT_FOUND));
        if(order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_FOUND + " for orderId");
        }
        Notification notification = message -> System.out.println("Notification: " + message);
        return ResponseEntity.status(HttpStatus.OK).body(OrderController.payOrder(order, sum, sharedOrderRepository, notification));
    }

    @PostMapping("/products/add")
    public ResponseEntity<String> addProduct(@RequestParam(value = "name", defaultValue = HttpError.NOT_SPECIFIED) String name,
                                     @RequestParam(value = "price", defaultValue = HttpError.NOT_SPECIFIED) String price,
                                     @RequestParam(value = "parkName", defaultValue = HttpError.NOT_SPECIFIED) String parkName){
        if (name.equals(HttpError.NOT_SPECIFIED) || price.equals(HttpError.NOT_SPECIFIED) || parkName.equals(HttpError.NOT_SPECIFIED))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_SPECIFIED + ". You must have name, price and parkName");
        else if (Double.parseDouble(price) <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.ERROR_AMOUNT_SPECIFIED + " for Price");
        else if (parkRepository.findByName(parkName) == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_FOUND + " for ParkName");
        double priceParsed = Double.parseDouble(price);
        Product product = new Product(name, priceParsed, parkName);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body("Product " + name + " was added to " + parkName + " park");
    }

    @PostMapping("/order/place")
    public ResponseEntity<String> placeOrder(@RequestBody IndividualOrder order) { // RequestBody as JSON here
        System.out.println(order.getParkName());
        if(order.getProductList().isEmpty() ||
                order.getParkName().equals(HttpError.NOT_SPECIFIED) ||
                order.getAlleyNumber().equals(HttpError.NOT_SPECIFIED) ||
                order.getTotalPrice() <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_SPECIFIED + ". You must have products, parkId, customerName and alleyNumber");

        User customer = userRepository.findByEmail(order.getCustomerEmail()); // Get customer data based on its email
        if(customer == null || customer.getName() == null) // Customer confirmation
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_FOUND + " for customer");

        return OrderController.placeOrder(customer, order.getParkName(), order, order.getAlleyNumber(), individualOrderRepository, sharedOrderRepository);
    }

    @PutMapping("/products/update")
    public ResponseEntity<String> updateProduct(@RequestParam(value = "id", defaultValue = HttpError.NOT_SPECIFIED) String id,
                                @RequestParam(value = "name", defaultValue = HttpError.NOT_SPECIFIED) String name,
                                @RequestParam(value = "price", defaultValue = HttpError.NOT_SPECIFIED) String price,
                                @RequestParam(value = "parkName", defaultValue = HttpError.NOT_SPECIFIED) String parkName) {
        if (id.equals(HttpError.NOT_SPECIFIED))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_SPECIFIED + ". You must have an id.");
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(HttpError.NOT_FOUND));
        if (!name.equals(HttpError.NOT_SPECIFIED))
            product.setName(name);
        if (!price.equals(HttpError.NOT_SPECIFIED) && Double.parseDouble(price) > 0)
            product.setPrice(Double.parseDouble(price));
        if (!parkName.equals(HttpError.NOT_SPECIFIED)) {
            if (parkRepository.findByName(parkName) == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_FOUND + " for ParkName");
            product.setParkName(parkName);
        }

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body("Product " + name + " has been updated."
                + " Price: " + (price.equals(HttpError.NOT_SPECIFIED) ? product.getPrice() : price)
                + " Park Name: " + (parkName.equals(HttpError.NOT_SPECIFIED) ? product.getParkName() : parkName));
    }

    @PostMapping("/products/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam(value = "id", defaultValue = HttpError.NOT_SPECIFIED) String id) {
        if (id.equals(HttpError.NOT_SPECIFIED))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpError.NOT_SPECIFIED + ". You must have an id.");
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product of id " + id + " has been deleted");
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

    @GetMapping("/orders-by-alley")
    public List<SharedOrder> getOrdersForAlley(@RequestParam(value = "alleyNumber", defaultValue = HttpError.NOT_SPECIFIED) String alleyNumber) {
        if (alleyNumber.equals(HttpError.NOT_SPECIFIED))
            return sharedOrderRepository.findAll();
        return sharedOrderRepository.findByAlleyNumber(alleyNumber);
    }
}
