package com.ticketpass.backend.dto;

import com.ticketpass.backend.entity.TicketType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTicketLotRequest(
        @NotBlank String name,
        @NotNull @Positive BigDecimal price,
        @NotNull @Positive Integer quantity,
        @NotNull @Future LocalDateTime startAt,
        LocalDateTime endAt,
        @NotNull TicketType ticketType
) {
}