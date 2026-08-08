package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.CreateTicketLotRequest;
import com.ticketpass.backend.dto.TicketLotResponse;
import com.ticketpass.backend.service.TicketLotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/events/{eventId}/lots")
public class TicketLotController {

    private final TicketLotService ticketLotService;

    public TicketLotController(TicketLotService ticketLotService) {
        this.ticketLotService = ticketLotService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketLotResponse create(
            @PathVariable UUID eventId,
            @Valid @RequestBody CreateTicketLotRequest request
    ) {
        return ticketLotService.create(eventId, request);
    }
}