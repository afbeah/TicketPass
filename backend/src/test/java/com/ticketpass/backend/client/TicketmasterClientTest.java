package com.ticketpass.backend.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ticketpass.backend.dto.ticketmaster.TicketmasterSearchResponse;

@SpringBootTest
class TicketmasterClientTest {

    @Autowired
    private TicketmasterClient ticketmasterClient;

    @Test
    void shouldSearchEvents() {
        TicketmasterSearchResponse response = ticketmasterClient.searchEvents(
                "rock",
                "Rio de Janeiro"
        );

        assertNotNull(response);
        assertNotNull(response.embedded());
        assertNotNull(response.embedded().events());
        assertFalse(response.embedded().events().isEmpty());

        System.out.println(
                "Primeiro evento: " +
                        response.embedded().events().get(0).name()
        );
    }
}