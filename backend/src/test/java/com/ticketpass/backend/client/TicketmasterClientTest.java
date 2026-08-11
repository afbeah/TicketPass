package com.ticketpass.backend.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TicketmasterClientTest {

    @Autowired
    private TicketmasterClient ticketmasterClient;

    @Test
    void shouldSearchEvents() {
        String response = ticketmasterClient.searchEvents(
                "rock",
                "Rio de Janeiro"
        );

        assertNotNull(response);
        assertFalse(response.isBlank());

        System.out.println(response);
    }
}