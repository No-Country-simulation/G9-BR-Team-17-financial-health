package com.nidus.controller;

import com.nidus.dto.AnaliseFinanceiraRequest;
import com.nidus.dto.AnaliseFinanceiraResponse;
import com.nidus.service.AnaliseFinanceiraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnaliseFinanceiraController {

    private final AnaliseFinanceiraService service;

    public AnaliseFinanceiraController(AnaliseFinanceiraService service) {
        this.service = service;
    }

    @PostMapping("/analise-financeira")
    public ResponseEntity<AnaliseFinanceiraResponse> analisar(
            @Valid @RequestBody AnaliseFinanceiraRequest request) {
        var response = service.analisar(request);
        return ResponseEntity.ok(response);
    }
}
