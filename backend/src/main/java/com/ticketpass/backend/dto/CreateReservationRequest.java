package com.ticketpass.backend.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateReservationRequest(
        @NotNull UUID customerId,
        @NotNull UUID ticketId
) {
}