package com.nidus.dto;

import java.util.List;

public class ClassificacaoTransacoesResponse {

    private List<TransacaoClassificada> transacoesClassificadas;

    public ClassificacaoTransacoesResponse() {}

    public ClassificacaoTransacoesResponse(List<TransacaoClassificada> transacoesClassificadas) {
        this.transacoesClassificadas = transacoesClassificadas;
    }

    public List<TransacaoClassificada> getTransacoesClassificadas() { return transacoesClassificadas; }
    public void setTransacoesClassificadas(List<TransacaoClassificada> transacoesClassificadas) {
        this.transacoesClassificadas = transacoesClassificadas;
    }

    public static class TransacaoClassificada {
        private String descricao;
        private java.math.BigDecimal valor;
        private String categoria;

        public TransacaoClassificada() {}

        public TransacaoClassificada(String descricao, java.math.BigDecimal valor, String categoria) {
            this.descricao = descricao;
            this.valor = valor;
            this.categoria = categoria;
        }

        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public java.math.BigDecimal getValor() { return valor; }
        public void setValor(java.math.BigDecimal valor) { this.valor = valor; }
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
    }
}
