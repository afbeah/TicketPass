package com.ticketpass.backend.dto.ticketmaster;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TicketmasterSearchResponse(
        @JsonProperty("_embedded")
        TicketmasterEmbeddedEventsResponse embedded,

        TicketmasterPageResponse page
) {
}