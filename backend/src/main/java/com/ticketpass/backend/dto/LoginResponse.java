package com.ticketpass.backend.dto;

public record LoginResponse(
        String token,
        String type,
        String role
) {
}