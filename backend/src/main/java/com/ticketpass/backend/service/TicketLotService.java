package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.CreateTicketLotRequest;
import com.ticketpass.backend.dto.TicketLotResponse;
import com.ticketpass.backend.entity.Event;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.TicketLot;
import com.ticketpass.backend.entity.TicketStatus;
import com.ticketpass.backend.repository.EventRepository;
import com.ticketpass.backend.repository.TicketLotRepository;
import com.ticketpass.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TicketLotService {

    private final EventRepository eventRepository;
    private final TicketLotRepository ticketLotRepository;
    private final TicketRepository ticketRepository;

    public TicketLotService(
            EventRepository eventRepository,
            TicketLotRepository ticketLotRepository,
            TicketRepository ticketRepository
    ) {
        this.eventRepository = eventRepository;
        this.ticketLotRepository = ticketLotRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketLotResponse create(
            UUID eventId,
            CreateTicketLotRequest request
    ) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Evento não encontrado"));

        if (event.getCapacity() < request.quantity()) {
            throw new IllegalArgumentException(
                    "A quantidade do lote excede a capacidade do evento"
            );
        }

        TicketLot lot = new TicketLot();

        lot.setEvent(event);
        lot.setName(request.name());
        lot.setPrice(request.price());
        lot.setQuantity(request.quantity());
        lot.setStartAt(request.startAt());
        lot.setEndAt(request.endAt());
        lot.setTicketType(request.ticketType());

        TicketLot savedLot = ticketLotRepository.save(lot);

        for (int i = 0; i < request.quantity(); i++) {
            Ticket ticket = new Ticket();

            ticket.setTicketLot(savedLot);
            ticket.setType(request.ticketType());
            ticket.setPrice(request.price());
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setQrCode(UUID.randomUUID().toString());

            ticketRepository.save(ticket);
        }

        return new TicketLotResponse(
                savedLot.getId(),
                event.getId(),
                savedLot.getName(),
                savedLot.getPrice(),
                savedLot.getQuantity(),
                savedLot.getStartAt(),
                savedLot.getEndAt(),
                savedLot.getStatus()
        );
    }
}