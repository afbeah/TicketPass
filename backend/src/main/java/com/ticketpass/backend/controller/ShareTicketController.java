package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.MyTicketResponse;
import com.ticketpass.backend.dto.ShareTicketResponse;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.service.ShareTicketService;
import com.ticketpass.backend.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class ShareTicketController {

    private final ShareTicketService shareTicketService;
    private final TicketService ticketService;

    public ShareTicketController(
            ShareTicketService shareTicketService,
            TicketService ticketService
    ) {
        this.shareTicketService = shareTicketService;
        this.ticketService = ticketService;
    }

    @PostMapping("/{ticketId}/share")
    public ShareTicketResponse share(
            @PathVariable UUID ticketId
    ) {
        return shareTicketService.generateShareLink(ticketId);
    }

    @GetMapping("/share/{shareToken}")
    public MyTicketResponse getSharedTicket(
            @PathVariable String shareToken
    ) {
        Ticket ticket =
                shareTicketService.findSharedTicket(shareToken);

        return ticketService.toResponse(ticket);
    }
}