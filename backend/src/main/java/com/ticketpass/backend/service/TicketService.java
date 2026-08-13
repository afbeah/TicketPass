package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.MyTicketResponse;
import com.ticketpass.backend.entity.Reservation;
import com.ticketpass.backend.entity.ReservationStatus;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.User;
import com.ticketpass.backend.repository.ReservationRepository;
import com.ticketpass.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public TicketService(
            ReservationRepository reservationRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public List<MyTicketResponse> findMyTickets(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuário não encontrado"));

        List<Reservation> reservations =
                reservationRepository.findByCustomer_IdAndStatus(
                        user.getId(),
                        ReservationStatus.CONFIRMED
                );

        return reservations.stream()
                .flatMap(reservation -> reservation.getTickets().stream())
                .map(this::toResponse)
                .toList();
    }

    private MyTicketResponse toResponse(Ticket ticket) {

        var event = ticket.getTicketLot().getEvent();

        return new MyTicketResponse(
                ticket.getId(),
                event.getName(),
                event.getLocation(),
                event.getStartDateTime(),
                ticket.getType().name(),
                ticket.getPrice(),
                ticket.getStatus(),
                ticket.getQrCode()
        );
    }
}