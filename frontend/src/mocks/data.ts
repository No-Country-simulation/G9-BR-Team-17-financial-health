export const analiseFinanceiraMock = {
  perfilFinanceiro: "Em observacao",
  probabilidade: 0.82,
  resumoGastos: {
    alimentacao: 420,
    transporte: 300,
    lazer: 40,
  },
  padroesIdentificados: [
    "Categoria de maior gasto: Alimentacao",
    "Comprometimento de renda com gastos essenciais: 16%",
  ],
  recomendacoes: [
    "Monitorar gastos recorrentes em Alimentacao",
    "Aumentar reserva financeira mensal",
  ],
};

export const classificacaoMock = {
  transacoesClassificadas: [
    { descricao: "Supermercado", valor: 420, categoria: "Alimentacao" },
    { descricao: "Combustivel", valor: 300, categoria: "Transporte" },
  ],
};
