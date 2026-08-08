package com.ticketpass.backend.dto;

import com.ticketpass.backend.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePaymentRequest(
        @NotNull UUID reservationId,
        @NotNull PaymentMethod method
) {
}