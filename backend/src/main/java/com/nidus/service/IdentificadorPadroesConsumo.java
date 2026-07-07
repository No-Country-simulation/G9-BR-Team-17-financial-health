package com.nidus.service;

import com.nidus.dto.MlAnaliseResponse;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IdentificadorPadroesConsumo {

    private static final BigDecimal LIMIAR_CONCENTRACAO = new BigDecimal("30");
    private static final BigDecimal LIMIAR_ATIPICO = new BigDecimal("2");
    private static final List<String> CATEGORIAS_ESSENCIAIS = List.of(
        "Alimentacao", "Moradia", "Saude", "Transporte", "Educacao");
    private static final List<String> CATEGORIAS_NAO_ESSENCIAIS = List.of("Lazer", "Servicos");

    public List<String> identificar(MlAnaliseResponse mlResponse, BigDecimal rendaMensal) {
        var padroes = new ArrayList<String>();

        var transacoes = mlResponse.getTransacoesClassificadas();
        if (transacoes == null || transacoes.isEmpty()) return padroes;

        var gastosPorCategoria = new HashMap<String, BigDecimal>();
        var somaTotal = BigDecimal.ZERO;

        for (var t : transacoes) {
            gastosPorCategoria.merge(t.getCategoria(), t.getValor(), BigDecimal::add);
            somaTotal = somaTotal.add(t.getValor());
        }

        var somaEssenciais = BigDecimal.ZERO;
        var somaNaoEssenciais = BigDecimal.ZERO;

        for (var entry : gastosPorCategoria.entrySet()) {
            var cat = entry.getKey();
            var valor = entry.getValue();

            var percentual = somaTotal.compareTo(BigDecimal.ZERO) > 0
                ? valor.multiply(new BigDecimal("100")).divide(somaTotal, 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

            if (percentual.compareTo(LIMIAR_CONCENTRACAO) > 0) {
                var fmt = new DecimalFormat("#,##0.##", new DecimalFormatSymbols(Locale.of("pt", "BR")));
                padroes.add("Concentracao em " + cat + " (" + fmt.format(percentual) + "% do total gasto)");
            }

            if (CATEGORIAS_ESSENCIAIS.contains(cat)) {
                somaEssenciais = somaEssenciais.add(valor);
            } else if (CATEGORIAS_NAO_ESSENCIAIS.contains(cat)) {
                somaNaoEssenciais = somaNaoEssenciais.add(valor);
            }
        }

        if (rendaMensal.compareTo(BigDecimal.ZERO) > 0) {
            var pctEssencial = somaEssenciais.multiply(new BigDecimal("100"))
                .divide(rendaMensal, 2, RoundingMode.HALF_UP);
            padroes.add("Comprometimento de renda com gastos essenciais: "
                + pctEssencial.setScale(0, RoundingMode.HALF_UP) + "%");

            var pctNaoEssencial = somaNaoEssenciais.multiply(new BigDecimal("100"))
                .divide(rendaMensal, 2, RoundingMode.HALF_UP);
            padroes.add("Gastos nao essenciais comprometem "
                + pctNaoEssencial.setScale(0, RoundingMode.HALF_UP) + "% da renda");
        }

        var descricoes = transacoes.stream()
            .map(t -> normalizar(t.getDescricao()))
            .collect(Collectors.toList());
        var contagem = new HashMap<String, Integer>();
        for (var d : descricoes) {
            contagem.merge(d, 1, Integer::sum);
        }
        for (var entry : contagem.entrySet()) {
            if (entry.getValue() > 1) {
                padroes.add("Padrao recorrente: " + entry.getKey()
                    + " (" + entry.getValue() + " ocorrencias)");
            }
        }

        if (transacoes.size() > 1) {
            var media = somaTotal.divide(BigDecimal.valueOf(transacoes.size()), 2, RoundingMode.HALF_UP);
            var limiteAtipico = media.multiply(LIMIAR_ATIPICO);
            for (var t : transacoes) {
                if (t.getValor().compareTo(limiteAtipico) > 0) {
                    padroes.add("Transacao atipica: " + t.getDescricao()
                        + " (valor muito acima da media)");
                }
            }
        }

        var categoriaDominante = gastosPorCategoria.entrySet().stream()
            .max(Map.Entry.comparingByValue());
        if (categoriaDominante.isPresent()) {
            padroes.add("Categoria de maior gasto: " + categoriaDominante.get().getKey());
        }

        return padroes;
    }

    private String normalizar(String texto) {
        if (texto == null) return "";
        return texto.trim().toLowerCase()
            .replaceAll("[^a-z0-9\\s]", "")
            .replaceAll("\\s+", " ");
    }
}
