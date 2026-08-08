package com.ticketpass.backend.config;

import com.ticketpass.backend.entity.User;
import com.ticketpass.backend.entity.UserRole;
import com.ticketpass.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User organizer = new User();
                organizer.setName("Organizador Demo");
                organizer.setEmail("organizador@ticketpass.com");
                organizer.setPassword("123456");
                organizer.setRole(UserRole.ORGANIZER);

                userRepository.save(organizer);
            }
        };
    }
}