package com.ticketpass.backend.dto;

public record GateValidationResponse(
        String status,
        String message
) {
}