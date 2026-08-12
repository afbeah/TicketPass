package com.ticketpass.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ValidateTicketRequest(
        @NotBlank String qrCode,
        @NotNull UUID eventId
) {
}