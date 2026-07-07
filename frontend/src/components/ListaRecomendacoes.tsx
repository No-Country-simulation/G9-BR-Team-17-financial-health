interface ListaRecomendacoesProps {
  recomendacoes: string[];
}

export default function ListaRecomendacoes({ recomendacoes }: ListaRecomendacoesProps) {
  return (
    <div style={{ marginBottom: "1rem" }}>
      <h3>Recomendações</h3>
      <ul>
        {recomendacoes.map((r, i) => (
          <li key={i}>{r}</li>
        ))}
      </ul>
    </div>
  );
}
