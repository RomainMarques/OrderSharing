package org.example.ordersharing;

import org.example.ordersharing.model.Order;
import org.example.ordersharing.model.User;
import org.example.ordersharing.repository.OrderRepository;
import org.example.ordersharing.repository.UserRepository;
import org.example.ordersharing.utils.HttpError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class OrderSharingApplication {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

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
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/get-orders-by-park")
    public List<Order> listOrdersByPark(@RequestParam(value = "parkName", defaultValue = HttpError.NOT_SPECIFIED) String parkName) {
        if (parkName.equals(HttpError.NOT_SPECIFIED)) {
            throw new IllegalArgumentException(HttpError.NOT_SPECIFIED);
        }
        return orderRepository.findByParkName(parkName);
    }
}
