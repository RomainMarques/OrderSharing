/*package org.example.ordersharing.config;

import org.example.ordersharing.model.User;
import org.example.ordersharing.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return strings -> {
            userRepository.save(new User(1, "Calvin", "agent"));
            userRepository.save(new User(2, "Bryson", "customer"));
        };
    }
}
*/