package com.ticketpass.backend.dto;

import com.ticketpass.backend.entity.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MyTicketResponse(
        UUID ticketId,
        String eventName,
        String location,
        LocalDateTime startDateTime,
        String ticketType,
        BigDecimal price,
        TicketStatus status,
        String qrCode
) {
}