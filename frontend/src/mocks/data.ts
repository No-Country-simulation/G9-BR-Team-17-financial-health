export const analiseFinanceiraMock = {
  perfil_financeiro: "Em observacao",
  probabilidade: 0.82,
  resumo_gastos: {
    alimentacao: 420,
    transporte: 300,
    lazer: 40,
  },
  padroes_identificados: [
    "Categoria de maior gasto: Alimentacao",
    "Comprometimento de renda com gastos essenciais: 16%",
  ],
  recomendacoes: [
    "Monitorar gastos recorrentes em Alimentacao",
    "Aumentar reserva financeira mensal",
  ],
};

export const classificacaoMock = {
  transacoes_classificadas: [
    { descricao: "Supermercado", valor: 420, categoria: "Alimentacao" },
    { descricao: "Combustivel", valor: 300, categoria: "Transporte" },
  ],
};
