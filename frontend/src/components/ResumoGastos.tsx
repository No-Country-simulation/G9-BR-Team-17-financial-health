interface ResumoGastosProps {
  gastos: Record<string, number>;
}

export default function ResumoGastos({ gastos }: ResumoGastosProps) {
  const categorias = Object.entries(gastos);

  if (categorias.length === 0) return null;

  return (
    <div style={{ marginBottom: "1rem" }}>
      <h3>Resumo de Gastos</h3>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={thStyle}>Categoria</th>
            <th style={{ ...thStyle, textAlign: "right" }}>Valor</th>
          </tr>
        </thead>
        <tbody>
          {categorias.map(([cat, valor]) => (
            <tr key={cat}>
              <td style={tdStyle}>{cat}</td>
              <td style={{ ...tdStyle, textAlign: "right" }}>{valor.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

const thStyle: React.CSSProperties = {
  borderBottom: "2px solid #ddd",
  padding: "0.5rem",
  textAlign: "left",
};

const tdStyle: React.CSSProperties = {
  borderBottom: "1px solid #eee",
  padding: "0.5rem",
};
