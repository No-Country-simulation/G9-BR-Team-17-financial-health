package com.nidus.dto;

import java.time.Instant;

public class ErroResponse {

    private Erro erro;

    public ErroResponse() {}

    public ErroResponse(String codigo, String mensagem, String campo) {
        this.erro = new Erro(codigo, mensagem, campo, Instant.now());
    }

    public Erro getErro() { return erro; }
    public void setErro(Erro erro) { this.erro = erro; }

    public static class Erro {
        private String codigo;
        private String mensagem;
        private String campo;
        private Instant timestamp;

        public Erro() {}

        public Erro(String codigo, String mensagem, String campo, Instant timestamp) {
            this.codigo = codigo;
            this.mensagem = mensagem;
            this.campo = campo;
            this.timestamp = timestamp;
        }

        public String getCodigo() { return codigo; }
        public void setCodigo(String codigo) { this.codigo = codigo; }
        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }
        public String getCampo() { return campo; }
        public void setCampo(String campo) { this.campo = campo; }
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    }
}
