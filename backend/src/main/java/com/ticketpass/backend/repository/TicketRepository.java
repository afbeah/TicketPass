package com.ticketpass.backend.repository;

import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.entity.TicketStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Optional<Ticket> findByQrCode(String qrCode);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Ticket t
            SET t.status = :reserved
            WHERE t.id = :ticketId
              AND t.status = :available
            """)
    int reserveTicket(
            @Param("ticketId") UUID ticketId,
            @Param("available") TicketStatus available,
            @Param("reserved") TicketStatus reserved
    );

    List<Ticket> findByTicketLotEventIdAndStatus(
            UUID eventId,
            TicketStatus status
    );
}