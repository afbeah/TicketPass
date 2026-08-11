package com.ticketpass.backend.dto.ticketmaster;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TicketmasterEmbeddedResponse(
        @JsonProperty("venues")
        List<TicketmasterVenueResponse> venues
) {
}