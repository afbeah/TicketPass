package com.ticketpass.backend.dto;

import com.ticketpass.backend.entity.TicketLotStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketLotResponse(
        UUID id,
        UUID eventId,
        String name,
        BigDecimal price,
        Integer quantity,
        LocalDateTime startAt,
        LocalDateTime endAt,
        TicketLotStatus status
) {
}