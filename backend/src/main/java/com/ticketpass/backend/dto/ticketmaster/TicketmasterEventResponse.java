package com.ticketpass.backend.dto.ticketmaster;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TicketmasterEventResponse(
        @JsonProperty("id")
        String id,

        @JsonProperty("name")
        String name,

        @JsonProperty("url")
        String url,

        @JsonProperty("images")
        List<TicketmasterImageResponse> images,

        @JsonProperty("dates")
        TicketmasterDatesResponse dates,

        @JsonProperty("_embedded")
        TicketmasterEmbeddedResponse embedded
) {
}