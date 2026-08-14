package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.GateValidationRequest;
import com.ticketpass.backend.dto.GateValidationResponse;
import com.ticketpass.backend.entity.Event;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.TicketStatus;
import com.ticketpass.backend.repository.EventRepository;
import com.ticketpass.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GateService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public GateService(
            TicketRepository ticketRepository,
            EventRepository eventRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public GateValidationResponse validate(
            GateValidationRequest request
    ) {
        Event event = eventRepository.findById(request.eventId())
                .orElse(null);

        if (event == null) {
            return new GateValidationResponse(
                    "INVALID",
                    "Evento não encontrado."
            );
        }

        Ticket ticket = ticketRepository
                .findByQrCode(request.qrCode())
                .orElse(null);

        if (ticket == null) {
            return new GateValidationResponse(
                    "INVALID",
                    "Ingresso inválido."
            );
        }

        if (ticket.getStatus() == TicketStatus.VALIDATED) {
            return new GateValidationResponse(
                    "ALREADY_USED",
                    "Este ingresso já foi utilizado."
            );
        }

        if (ticket.getTicketLot() == null
                || ticket.getTicketLot().getEvent() == null
                || !ticket.getTicketLot()
                .getEvent()
                .getId()
                .equals(event.getId())) {

            return new GateValidationResponse(
                    "WRONG_EVENT",
                    "Este ingresso pertence a outro evento."
            );
        }

        if (ticket.getStatus() != TicketStatus.SOLD) {
            return new GateValidationResponse(
                    "INVALID",
                    "Este ingresso não está disponível para entrada."
            );
        }

        ticket.setStatus(TicketStatus.VALIDATED);

        ticketRepository.save(ticket);

        return new GateValidationResponse(
                "VALID",
                "Ingresso válido. Entrada liberada."
        );
    }
}