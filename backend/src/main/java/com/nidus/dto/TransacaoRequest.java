package com.nidus.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class TransacaoRequest {

    @NotBlank(message = "Descricao da transacao e obrigatoria")
    @Size(max = 120, message = "Descricao deve ter no maximo 120 caracteres")
    private String descricao;

    @DecimalMin(value = "0.01", message = "Valor da transacao deve ser maior que zero")
    private BigDecimal valor;

    public TransacaoRequest() {}

    public TransacaoRequest(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
