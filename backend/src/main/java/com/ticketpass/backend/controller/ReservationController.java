package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.CreateReservationRequest;
import com.ticketpass.backend.entity.Reservation;
import com.ticketpass.backend.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(
            @Valid @RequestBody CreateReservationRequest request
    ) {
        return reservationService.create(request);
    }
}