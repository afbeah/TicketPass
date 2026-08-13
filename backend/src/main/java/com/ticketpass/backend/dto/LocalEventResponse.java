package com.ticketpass.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record LocalEventResponse(
        UUID eventId,
        String name,
        String description,
        String location,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        UUID ticketId,
        BigDecimal ticketPrice
) {
}