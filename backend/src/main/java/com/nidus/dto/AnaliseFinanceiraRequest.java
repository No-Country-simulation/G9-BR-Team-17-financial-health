package com.nidus.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

public class AnaliseFinanceiraRequest {

    @DecimalMin(value = "0.01", message = "Renda mensal deve ser maior que zero")
    private BigDecimal rendaMensal;

    @DecimalMin(value = "0", message = "Nivel de endividamento deve estar entre 0 e 100")
    @DecimalMax(value = "100", message = "Nivel de endividamento deve estar entre 0 e 100")
    private BigDecimal nivelEndividamento;

    @NotBlank(message = "Frequencia de poupanca e obrigatoria")
    @Pattern(regexp = "Nenhuma|Baixa|Media|Alta",
             message = "Frequencia de poupanca deve ser: Nenhuma, Baixa, Media ou Alta")
    private String frequenciaPoupanca;

    @NotEmpty(message = "E necessario informar ao menos uma transacao")
    @Valid
    private List<TransacaoRequest> transacoes;

    public AnaliseFinanceiraRequest() {}

    public BigDecimal getRendaMensal() { return rendaMensal; }
    public void setRendaMensal(BigDecimal rendaMensal) { this.rendaMensal = rendaMensal; }
    public BigDecimal getNivelEndividamento() { return nivelEndividamento; }
    public void setNivelEndividamento(BigDecimal nivelEndividamento) { this.nivelEndividamento = nivelEndividamento; }
    public String getFrequenciaPoupanca() { return frequenciaPoupanca; }
    public void setFrequenciaPoupanca(String frequenciaPoupanca) { this.frequenciaPoupanca = frequenciaPoupanca; }
    public List<TransacaoRequest> getTransacoes() { return transacoes; }
    public void setTransacoes(List<TransacaoRequest> transacoes) { this.transacoes = transacoes; }
}
