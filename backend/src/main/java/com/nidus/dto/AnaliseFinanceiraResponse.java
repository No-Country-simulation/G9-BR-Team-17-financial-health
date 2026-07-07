package com.nidus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AnaliseFinanceiraResponse {

    private String perfilFinanceiro;
    private BigDecimal probabilidade;
    private Map<String, BigDecimal> resumoGastos;
    private List<String> padroesIdentificados;
    private List<String> recomendacoes;

    public AnaliseFinanceiraResponse() {}

    public AnaliseFinanceiraResponse(String perfilFinanceiro, BigDecimal probabilidade,
                                     Map<String, BigDecimal> resumoGastos,
                                     List<String> padroesIdentificados,
                                     List<String> recomendacoes) {
        this.perfilFinanceiro = perfilFinanceiro;
        this.probabilidade = probabilidade;
        this.resumoGastos = resumoGastos;
        this.padroesIdentificados = padroesIdentificados;
        this.recomendacoes = recomendacoes;
    }

    public String getPerfilFinanceiro() { return perfilFinanceiro; }
    public void setPerfilFinanceiro(String perfilFinanceiro) { this.perfilFinanceiro = perfilFinanceiro; }
    public BigDecimal getProbabilidade() { return probabilidade; }
    public void setProbabilidade(BigDecimal probabilidade) { this.probabilidade = probabilidade; }
    public Map<String, BigDecimal> getResumoGastos() { return resumoGastos; }
    public void setResumoGastos(Map<String, BigDecimal> resumoGastos) { this.resumoGastos = resumoGastos; }
    public List<String> getPadroesIdentificados() { return padroesIdentificados; }
    public void setPadroesIdentificados(List<String> padroesIdentificados) { this.padroesIdentificados = padroesIdentificados; }
    public List<String> getRecomendacoes() { return recomendacoes; }
    public void setRecomendacoes(List<String> recomendacoes) { this.recomendacoes = recomendacoes; }
}
