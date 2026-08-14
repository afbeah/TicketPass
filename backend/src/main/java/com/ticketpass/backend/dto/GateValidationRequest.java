package com.ticketpass.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GateValidationRequest(
        @NotBlank String qrCode,
        @NotNull UUID eventId
) {
}