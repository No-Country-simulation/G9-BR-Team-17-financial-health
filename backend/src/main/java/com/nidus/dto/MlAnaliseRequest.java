package com.nidus.dto;

import java.math.BigDecimal;
import java.util.List;

public class MlAnaliseRequest {

    private BigDecimal rendaMensal;
    private BigDecimal nivelEndividamento;
    private String frequenciaPoupanca;
    private List<TransacaoRequest> transacoes;

    public MlAnaliseRequest() {}

    public MlAnaliseRequest(BigDecimal rendaMensal, BigDecimal nivelEndividamento,
                            String frequenciaPoupanca, List<TransacaoRequest> transacoes) {
        this.rendaMensal = rendaMensal;
        this.nivelEndividamento = nivelEndividamento;
        this.frequenciaPoupanca = frequenciaPoupanca;
        this.transacoes = transacoes;
    }

    public BigDecimal getRendaMensal() { return rendaMensal; }
    public void setRendaMensal(BigDecimal rendaMensal) { this.rendaMensal = rendaMensal; }
    public BigDecimal getNivelEndividamento() { return nivelEndividamento; }
    public void setNivelEndividamento(BigDecimal nivelEndividamento) { this.nivelEndividamento = nivelEndividamento; }
    public String getFrequenciaPoupanca() { return frequenciaPoupanca; }
    public void setFrequenciaPoupanca(String frequenciaPoupanca) { this.frequenciaPoupanca = frequenciaPoupanca; }
    public List<TransacaoRequest> getTransacoes() { return transacoes; }
    public void setTransacoes(List<TransacaoRequest> transacoes) { this.transacoes = transacoes; }
}
