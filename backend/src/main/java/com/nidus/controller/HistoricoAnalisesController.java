package com.nidus.controller;

import com.nidus.repository.AnaliseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class HistoricoAnalisesController {

    private final AnaliseRepository repository;

    public HistoricoAnalisesController(AnaliseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/historico-analises")
    public ResponseEntity<HistoricoResponse> listar() {
        var analises = repository.findAllByOrderByCriadoEmDesc();
        var historico = analises.stream()
            .map(a -> new HistoricoItem(a.getId().toString(), a.getCriadoEm().toString(),
                a.getPerfilFinanceiro(), a.getResumoGastos()))
            .toList();
        return ResponseEntity.ok(new HistoricoResponse(historico));
    }

    public record HistoricoResponse(List<HistoricoItem> analises) {}
    public record HistoricoItem(String id, String criadoEm, String perfilFinanceiro,
                                java.util.Map<String, java.math.BigDecimal> resumoGastos) {}
}
