package com.ticketpass.backend.service;

import com.ticketpass.backend.dto.CreatePaymentRequest;
import com.ticketpass.backend.entity.Payment;
import com.ticketpass.backend.entity.PaymentStatus;
import com.ticketpass.backend.entity.Reservation;
import com.ticketpass.backend.entity.ReservationStatus;
import com.ticketpass.backend.entity.Ticket;
import com.ticketpass.backend.repository.PaymentRepository;
import com.ticketpass.backend.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            ReservationRepository reservationRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Payment create(CreatePaymentRequest request) {

        Reservation reservation = reservationRepository.findById(request.reservationId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Reserva não encontrada"));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException(
                    "A reserva não está disponível para pagamento"
            );
        }

        BigDecimal amount = reservation.getTickets()
                .stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Payment payment = new Payment();

        payment.setReservation(reservation);
        payment.setMethod(request.method());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(amount);

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment approve(UUID paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Pagamento não encontrado"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "O pagamento não está pendente"
            );
        }

        Reservation reservation = payment.getReservation();

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException(
                    "A reserva não está ativa"
            );
        }

        payment.setStatus(PaymentStatus.APPROVED);
        payment.setProcessedAt(java.time.LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());

        reservation.setStatus(ReservationStatus.CONFIRMED);

        for (Ticket ticket : reservation.getTickets()) {
            ticket.setStatus(com.ticketpass.backend.entity.TicketStatus.SOLD);
        }

        reservationRepository.save(reservation);

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment decline(UUID paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Pagamento não encontrado"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "O pagamento não está pendente"
            );
        }

        Reservation reservation = payment.getReservation();

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException(
                    "A reserva não está ativa"
            );
        }

        payment.setStatus(PaymentStatus.DECLINED);
        payment.setProcessedAt(java.time.LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());

        reservation.setStatus(ReservationStatus.CANCELLED);

        reservationRepository.save(reservation);

        return paymentRepository.save(payment);
    }
}