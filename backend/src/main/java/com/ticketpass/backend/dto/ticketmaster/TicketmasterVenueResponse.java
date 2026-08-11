package com.ticketpass.backend.dto.ticketmaster;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TicketmasterVenueResponse(
        String name,
        TicketmasterCityResponse city,
        TicketmasterStateResponse state,
        TicketmasterAddressResponse address
) {
}