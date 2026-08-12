package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.ShareTicketResponse;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ShareTicketService {

    private final TicketRepository ticketRepository;

    public ShareTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public ShareTicketResponse generateShareLink(UUID ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Ingresso não encontrado"));

        if (ticket.getShareToken() == null) {
            ticket.setShareToken(UUID.randomUUID().toString());
            ticketRepository.save(ticket);
        }

        return new ShareTicketResponse(
                "/api/tickets/share/" + ticket.getShareToken()
        );
    }
}