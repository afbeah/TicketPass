package com.ticketpass.backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateEventRequest(
        @NotNull UUID organizerId,
        @NotBlank String name,
        String description,
        @NotBlank String location,
        @NotNull @Future LocalDateTime startDateTime,
        @NotNull @Future LocalDateTime endDateTime,
        @NotNull @Positive Integer capacity
) {
}