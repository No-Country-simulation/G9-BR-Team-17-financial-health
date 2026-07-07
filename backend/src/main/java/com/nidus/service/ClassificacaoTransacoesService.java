package com.nidus.service;

import com.nidus.dto.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassificacaoTransacoesService {

    private final MlServiceClient mlServiceClient;

    public ClassificacaoTransacoesService(MlServiceClient mlServiceClient) {
        this.mlServiceClient = mlServiceClient;
    }

    public ClassificacaoTransacoesResponse classificar(ClassificacaoTransacoesRequest request) {
        var analiseRequest = new AnaliseFinanceiraRequest();
        analiseRequest.setRendaMensal(new java.math.BigDecimal("1000"));
        analiseRequest.setNivelEndividamento(java.math.BigDecimal.ZERO);
        analiseRequest.setFrequenciaPoupanca("Media");
        analiseRequest.setTransacoes(request.getTransacoes());

        var mlRequest = new MlAnaliseRequest(
            analiseRequest.getRendaMensal(),
            analiseRequest.getNivelEndividamento(),
            analiseRequest.getFrequenciaPoupanca(),
            analiseRequest.getTransacoes()
        );

        var mlResponse = mlServiceClient.analisar(mlRequest);

        var classificadas = mlResponse.getTransacoesClassificadas().stream()
            .map(t -> new ClassificacaoTransacoesResponse.TransacaoClassificada(
                t.getDescricao(), t.getValor(), t.getCategoria()))
            .toList();

        return new ClassificacaoTransacoesResponse(classificadas);
    }
}
