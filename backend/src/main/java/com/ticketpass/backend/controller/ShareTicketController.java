package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.ShareTicketResponse;
import com.ticketpass.backend.service.ShareTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class ShareTicketController {

    private final ShareTicketService shareTicketService;

    public ShareTicketController(ShareTicketService shareTicketService) {
        this.shareTicketService = shareTicketService;
    }

    @PostMapping("/{ticketId}/share")
    public ShareTicketResponse generateShareLink(
            @PathVariable UUID ticketId
    ) {
        return shareTicketService.generateShareLink(ticketId);
    }
}