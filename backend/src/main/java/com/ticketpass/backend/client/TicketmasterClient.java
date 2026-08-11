package com.ticketpass.backend.client;

import com.ticketpass.backend.config.TicketmasterConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.ticketpass.backend.dto.ticketmaster.TicketmasterSearchResponse;

@Component
public class TicketmasterClient {

    private final RestClient restClient;
    private final TicketmasterConfig config;

    public TicketmasterClient(
            RestClient restClient,
            TicketmasterConfig config
    ) {
        this.restClient = restClient;
        this.config = config;
    }

    public TicketmasterSearchResponse searchEvents(String keyword, String city) {
        return restClient.get()
                .uri(
                        config.getBaseUrl() + "/events.json" +
                                "?apikey={apiKey}" +
                                "&keyword={keyword}" +
                                "&city={city}",
                        config.getApiKey(),
                        keyword,
                        city
                )
                .retrieve()
                .body(TicketmasterSearchResponse.class);
    }
}