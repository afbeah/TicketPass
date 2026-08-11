package com.ticketpass.backend.dto;

public record TicketmasterEventResult(
        String id,
        String name,
        String url,
        String imageUrl,
        String date,
        String time,
        String venue,
        String city,
        String state
) {
}