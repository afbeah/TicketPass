package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.MyTicketResponse;
import com.ticketpass.backend.service.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/my")
    public List<MyTicketResponse> findMyTickets(
            Authentication authentication
    ) {
        return ticketService.findMyTickets(
                authentication.getName()
        );
    }
}