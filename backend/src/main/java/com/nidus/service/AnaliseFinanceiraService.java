package com.nidus.service;

import com.nidus.dto.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnaliseFinanceiraService {

    private final MlServiceClient mlServiceClient;
    private final IdentificadorPadroesConsumo identificadorPadroes;
    private final GeradorRecomendacoes geradorRecomendacoes;

    public AnaliseFinanceiraService(MlServiceClient mlServiceClient,
                                    IdentificadorPadroesConsumo identificadorPadroes,
                                    GeradorRecomendacoes geradorRecomendacoes) {
        this.mlServiceClient = mlServiceClient;
        this.identificadorPadroes = identificadorPadroes;
        this.geradorRecomendacoes = geradorRecomendacoes;
    }

    public AnaliseFinanceiraResponse analisar(AnaliseFinanceiraRequest request) {
        var mlRequest = new MlAnaliseRequest(
            request.getRendaMensal(),
            request.getNivelEndividamento(),
            request.getFrequenciaPoupanca(),
            request.getTransacoes()
        );

        var mlResponse = mlServiceClient.analisar(mlRequest);

        var resumoGastos = new HashMap<String, BigDecimal>();
        if (mlResponse.getTransacoesClassificadas() != null) {
            for (var t : mlResponse.getTransacoesClassificadas()) {
                var cat = t.getCategoria();
                resumoGastos.merge(cat, t.getValor(), BigDecimal::add);
            }
        }

        var padroes = identificadorPadroes.identificar(mlResponse, request.getRendaMensal());

        var recomendacoes = geradorRecomendacoes.gerar(
            mlResponse.getPerfilFinanceiro(),
            mlResponse.getProbabilidade(),
            request.getNivelEndividamento(),
            request.getFrequenciaPoupanca(),
            resumoGastos
        );

        return new AnaliseFinanceiraResponse(
            mlResponse.getPerfilFinanceiro(),
            mlResponse.getProbabilidade(),
            resumoGastos,
            padroes,
            recomendacoes
        );
    }
}
