interface ListaPadroesConsumoProps {
  padroes: string[];
}

export default function ListaPadroesConsumo({ padroes }: ListaPadroesConsumoProps) {
  if (padroes.length === 0) return null;

  return (
    <div style={{ marginBottom: "1rem" }}>
      <h3>Padrões de Consumo</h3>
      <ul>
        {padroes.map((p, i) => (
          <li key={i}>{p}</li>
        ))}
      </ul>
    </div>
  );
}
