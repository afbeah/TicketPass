package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.CreateReservationRequest;
import com.ticketpass.backend.entity.Reservation;
import com.ticketpass.backend.entity.ReservationStatus;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.TicketStatus;
import com.ticketpass.backend.entity.User;
import com.ticketpass.backend.repository.ReservationRepository;
import com.ticketpass.backend.repository.TicketRepository;
import com.ticketpass.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Reservation create(CreateReservationRequest request) {

        User customer = userRepository.findById(request.customerId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Cliente não encontrado"));

        Ticket ticket = ticketRepository.findById(request.ticketId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Ingresso não encontrado"));

        int updated = ticketRepository.reserveTicket(
                ticket.getId(),
                TicketStatus.AVAILABLE,
                TicketStatus.RESERVED
        );

        if (updated == 0) {
            throw new IllegalStateException(
                    "Ingresso não está disponível para reserva"
            );
        }

        ticket.setStatus(TicketStatus.RESERVED);

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setTickets(List.of(ticket));
        reservation.setStatus(ReservationStatus.ACTIVE);

        return reservationRepository.save(reservation);
    }
}