package com.ticketpass.backend.dto;

import com.ticketpass.backend.entity.EventStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse(
        UUID id,
        UUID organizerId,
        String name,
        String description,
        String location,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Integer capacity,
        EventStatus status
) {
}