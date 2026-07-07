CREATE TABLE IF NOT EXISTS analise (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    requisicao JSONB NOT NULL,
    resposta JSONB NOT NULL,
    perfil_financeiro VARCHAR(20) NOT NULL,
    probabilidade DECIMAL(4,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS transacao_classificada (
    id UUID PRIMARY KEY,
    analise_id UUID NOT NULL REFERENCES analise(id),
    descricao VARCHAR(120) NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    categoria VARCHAR(20) NOT NULL
);

CREATE INDEX idx_analise_criado_em ON analise(criado_em DESC);
