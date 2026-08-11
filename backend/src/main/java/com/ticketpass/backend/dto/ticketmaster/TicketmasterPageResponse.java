package com.ticketpass.backend.dto.ticketmaster;

public record TicketmasterPageResponse(
        int size,
        int totalElements,
        int totalPages,
        int number
) {
}