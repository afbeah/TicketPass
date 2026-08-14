package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.CreatePaymentRequest;
import com.ticketpass.backend.entity.Payment;
import com.ticketpass.backend.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        return paymentService.create(request);
    }

    @PutMapping("/{paymentId}/decline")
    public Payment decline(@PathVariable UUID paymentId) {
        return paymentService.decline(paymentId);
    }

    @PutMapping("/{paymentId}/approve")
    public Payment approve(@PathVariable UUID paymentId) {
        return paymentService.approve(paymentId);
    }
}
