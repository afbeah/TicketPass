package com.ticketpass.backend.config;

import com.ticketpass.backend.entity.Event;
import com.ticketpass.backend.entity.EventStatus;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.TicketLot;
import com.ticketpass.backend.entity.TicketLotStatus;
import com.ticketpass.backend.entity.TicketStatus;
import com.ticketpass.backend.entity.TicketType;
import com.ticketpass.backend.entity.User;
import com.ticketpass.backend.entity.UserRole;
import com.ticketpass.backend.repository.EventRepository;
import com.ticketpass.backend.repository.TicketLotRepository;
import com.ticketpass.backend.repository.TicketRepository;
import com.ticketpass.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(
            UserRepository userRepository,
            EventRepository eventRepository,
            TicketLotRepository ticketLotRepository,
            TicketRepository ticketRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            User organizer = createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Organizador Demo",
                    "organizador@ticketpass.com",
                    "123456",
                    UserRole.ORGANIZER
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Cliente Demo 1",
                    "cliente1@ticketpass.com",
                    "123456",
                    UserRole.CUSTOMER
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Cliente Demo 2",
                    "cliente2@ticketpass.com",
                    "123456",
                    UserRole.CUSTOMER
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Portaria Demo",
                    "portaria@ticketpass.com",
                    "123456",
                    UserRole.GATEKEEPER
            );

            createEventIfNotExists(
                    organizer,
                    eventRepository,
                    ticketLotRepository,
                    ticketRepository
            );
        };
    }

    private User createUserIfNotExists(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String name,
            String email,
            String password,
            UserRole role
    ) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User user = new User();

                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode(password));
                    user.setRole(role);

                    return userRepository.save(user);
                });
    }

    private void createEventIfNotExists(
            User organizer,
            EventRepository eventRepository,
            TicketLotRepository ticketLotRepository,
            TicketRepository ticketRepository
    ) {
        if (eventRepository.findByName(
                "TicketPass Demo - Rock Festival"
        ).isPresent()) {
            return;
        }

        Event event = new Event();

        event.setOrganizer(organizer);
        event.setName("TicketPass Demo - Rock Festival");
        event.setDescription(
                "Evento de demonstração do fluxo completo do TicketPass."
        );
        event.setLocation("Rio de Janeiro - RJ");
        event.setStartDateTime(
                LocalDateTime.now()
                        .plusDays(30)
                        .withHour(20)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)
        );
        event.setEndDateTime(
                LocalDateTime.now()
                        .plusDays(30)
                        .withHour(23)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)
        );
        event.setCapacity(100);
        event.setStatus(EventStatus.PUBLISHED);

        Event savedEvent = eventRepository.save(event);

        TicketLot lot = new TicketLot();

        lot.setEvent(savedEvent);
        lot.setName("Lote Promocional");
        lot.setPrice(new BigDecimal("80.00"));
        lot.setQuantity(20);
        lot.setStartAt(LocalDateTime.now());
        lot.setEndAt(LocalDateTime.now().plusDays(20));
        lot.setStatus(TicketLotStatus.ACTIVE);
        lot.setTicketType(TicketType.FULL);

        TicketLot savedLot = ticketLotRepository.save(lot);

        for (int i = 0; i < 20; i++) {
            Ticket ticket = new Ticket();

            ticket.setTicketLot(savedLot);
            ticket.setType(TicketType.FULL);
            ticket.setPrice(new BigDecimal("80.00"));
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setQrCode(UUID.randomUUID().toString());

            ticketRepository.save(ticket);
        }
    }
}