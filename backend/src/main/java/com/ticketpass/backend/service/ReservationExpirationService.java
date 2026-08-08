package com.ticketpass.backend.service;

import com.ticketpass.backend.entity.Reservation;
import com.ticketpass.backend.entity.ReservationStatus;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.TicketStatus;
import com.ticketpass.backend.repository.ReservationRepository;
import com.ticketpass.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationExpirationService {

    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;

    public ReservationExpirationService(
            ReservationRepository reservationRepository,
            TicketRepository ticketRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void expireReservations() {

        var expiredReservations =
                reservationRepository.findByStatusAndExpiresAtBefore(
                        ReservationStatus.ACTIVE,
                        LocalDateTime.now()
                );

        for (Reservation reservation : expiredReservations) {

            reservation.setStatus(ReservationStatus.EXPIRED);

            for (Ticket ticket : reservation.getTickets()) {
                ticket.setStatus(TicketStatus.AVAILABLE);
            }

            reservationRepository.save(reservation);
        }
    }
}