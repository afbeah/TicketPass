package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.ValidateTicketRequest;
import com.ticketpass.backend.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping
    public String validate(@Valid @RequestBody ValidateTicketRequest request) {
        return validationService.validate(request);
    }
}