package com.ticketpass.backend.dto;

import java.util.UUID;

public record ShareTicketResponse(
        UUID ticketId,
        String shareToken,
        String shareUrl
) {
}