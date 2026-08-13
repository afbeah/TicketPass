package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.CreateEventRequest;
import com.ticketpass.backend.dto.EventResponse;
import com.ticketpass.backend.dto.LocalEventResponse;
import com.ticketpass.backend.dto.TicketmasterEventResult;
import com.ticketpass.backend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse create(
            @Valid @RequestBody CreateEventRequest request
    ) {
        return eventService.create(request);
    }

    @GetMapping("/local")
    public List<LocalEventResponse> findLocalEvents() {
        return eventService.findLocalEvents();
    }

    @GetMapping
    public List<TicketmasterEventResult> searchExternalEvents(
            @RequestParam String keyword,
            @RequestParam String city
    ) {
        return eventService.searchExternalEvents(keyword, city);
    }
}