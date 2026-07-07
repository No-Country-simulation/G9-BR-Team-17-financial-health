package com.nidus.controller;

import com.nidus.dto.ClassificacaoTransacoesRequest;
import com.nidus.dto.ClassificacaoTransacoesResponse;
import com.nidus.service.ClassificacaoTransacoesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassificacaoTransacoesController {

    private final ClassificacaoTransacoesService service;

    public ClassificacaoTransacoesController(ClassificacaoTransacoesService service) {
        this.service = service;
    }

    @PostMapping("/classificacao-transacoes")
    public ResponseEntity<ClassificacaoTransacoesResponse> classificar(
            @Valid @RequestBody ClassificacaoTransacoesRequest request) {
        var response = service.classificar(request);
        return ResponseEntity.ok(response);
    }
}
