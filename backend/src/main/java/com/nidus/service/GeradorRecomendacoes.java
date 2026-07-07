package com.nidus.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GeradorRecomendacoes {

    public List<String> gerar(String perfilFinanceiro, BigDecimal probabilidade,
                              BigDecimal nivelEndividamento, String frequenciaPoupanca,
                              BigDecimal rendaMensal,
                              Map<String, BigDecimal> resumoGastos) {
        var recomendacoes = new ArrayList<String>();

        if ("Em risco".equals(perfilFinanceiro)) {
            recomendacoes.add("Priorizar quitacao de dividas para reduzir o comprometimento da renda");

            if ("Nenhuma".equals(frequenciaPoupanca)) {
                recomendacoes.add("Estabelecer meta minima de poupanca mensal, mesmo que o valor seja pequeno");
            }
        }

        if ("Em observacao".equals(perfilFinanceiro)
            && ("Baixa".equals(frequenciaPoupanca) || "Media".equals(frequenciaPoupanca))) {
            recomendacoes.add("Aumentar reserva financeira mensal");
        }

        if ("Saudavel".equals(perfilFinanceiro)) {
            if ("Alta".equals(frequenciaPoupanca)) {
                recomendacoes.add("Manter o padrao atual de poupanca e gastos");
                recomendacoes.add("Considerar reserva de emergencia adicional");
            } else if ("Media".equals(frequenciaPoupanca)) {
                recomendacoes.add("Considerar aumentar a reserva de emergencia");
            }
        }

        if (nivelEndividamento != null && nivelEndividamento.compareTo(new BigDecimal("40")) > 0) {
            recomendacoes.add("Reduzir o nivel de endividamento antes de assumir novos compromissos");
        }

        if (rendaMensal != null && rendaMensal.compareTo(BigDecimal.ZERO) > 0) {
            for (var entry : resumoGastos.entrySet()) {
                var cat = entry.getKey();
                var valor = entry.getValue();
                var pctRenda = valor.multiply(new BigDecimal("100"))
                    .divide(rendaMensal, 2, RoundingMode.HALF_UP);

                if ("Lazer".equals(cat) && pctRenda.compareTo(new BigDecimal("30")) > 0) {
                    recomendacoes.add("Reduzir gastos com lazer e entretenimento");
                }
                if ("Servicos".equals(cat) && pctRenda.compareTo(new BigDecimal("25")) > 0) {
                    recomendacoes.add("Revisar assinaturas e servicos contratados");
                }
            }
        }

        var categoriaMaisGasto = resumoGastos.entrySet().stream()
            .max(Map.Entry.comparingByValue());
        if (categoriaMaisGasto.isPresent()) {
            recomendacoes.add("Monitorar gastos recorrentes em " + categoriaMaisGasto.get().getKey());
        }

        if (recomendacoes.isEmpty()) {
            recomendacoes.add("Manter o acompanhamento regular dos seus gastos");
        }

        return recomendacoes;
    }
}
