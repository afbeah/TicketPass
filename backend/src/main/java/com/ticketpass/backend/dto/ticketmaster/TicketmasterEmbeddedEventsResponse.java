package com.ticketpass.backend.dto.ticketmaster;

import java.util.List;

public record TicketmasterEmbeddedEventsResponse(
        List<TicketmasterEventResponse> events
) {
}