package org.example.ordersharing;

import org.example.ordersharing.model.User;
import org.example.ordersharing.repository.UserRepository;
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

    public static void main(String[] args) {
        SpringApplication.run(OrderSharingApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/users")
    public List<User> list() {

        return userRepository.findAll();
    }
}
