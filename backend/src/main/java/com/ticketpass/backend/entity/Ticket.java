package com.ticketpass.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ticket_lot_id", nullable = false)
    private TicketLot ticketLot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.AVAILABLE;

    @Column(nullable = false, unique = true)
    private String qrCode;

    public UUID getId() {
        return id;
    }

    public TicketLot getTicketLot() {
        return ticketLot;
    }

    public void setTicketLot(TicketLot ticketLot) {
        this.ticketLot = ticketLot;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }


    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}