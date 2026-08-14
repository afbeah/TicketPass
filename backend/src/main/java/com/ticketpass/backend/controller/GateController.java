package com.ticketpass.backend.controller;

import com.ticketpass.backend.dto.GateValidationRequest;
import com.ticketpass.backend.dto.GateValidationResponse;
import com.ticketpass.backend.service.GateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gate")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @PostMapping("/validate")
    public GateValidationResponse validate(
            @Valid @RequestBody GateValidationRequest request
    ) {
        return gateService.validate(request);
    }
}