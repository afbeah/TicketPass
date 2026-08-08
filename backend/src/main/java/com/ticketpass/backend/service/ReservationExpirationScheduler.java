package com.ticketpass.backend.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationExpirationScheduler {

    private final ReservationExpirationService expirationService;

    public ReservationExpirationScheduler(
            ReservationExpirationService expirationService
    ) {
        this.expirationService = expirationService;
    }

    @Scheduled(fixedRate = 60000)
    public void expireReservations() {
        expirationService.expireReservations();
    }
}