package com.nidus.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class ClassificacaoTransacoesRequest {

    @NotEmpty(message = "E necessario informar ao menos uma transacao para classificacao")
    @Valid
    private List<TransacaoRequest> transacoes;

    public ClassificacaoTransacoesRequest() {}

    public List<TransacaoRequest> getTransacoes() { return transacoes; }
    public void setTransacoes(List<TransacaoRequest> transacoes) { this.transacoes = transacoes; }
}
