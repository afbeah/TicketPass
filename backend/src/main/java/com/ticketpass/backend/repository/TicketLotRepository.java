package com.ticketpass.backend.repository;

import com.ticketpass.backend.entity.TicketLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketLotRepository extends JpaRepository<TicketLot, UUID> {
}