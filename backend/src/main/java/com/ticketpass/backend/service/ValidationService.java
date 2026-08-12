package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.ValidateTicketRequest;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.TicketStatus;
import com.ticketpass.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValidationService {

    private final TicketRepository ticketRepository;

    public ValidationService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public String validate(ValidateTicketRequest request) {

        Ticket ticket = ticketRepository.findByQrCode(request.qrCode())
                .orElse(null);

        if (ticket == null) {
            return "Ingresso inválido";
        }

        if (ticket.getStatus() == TicketStatus.VALIDATED) {
            return "Ingresso já utilizado";
        }

        if (ticket.getStatus() != TicketStatus.SOLD) {
            return "Ingresso inválido";
        }

        if (!ticket.getTicketLot()
                .getEvent()
                .getId()
                .equals(request.eventId())) {
            return "Ingresso pertence a outro evento";
        }

        ticket.setStatus(TicketStatus.VALIDATED);

        ticketRepository.save(ticket);

        return "Ingresso válido";
    }
}