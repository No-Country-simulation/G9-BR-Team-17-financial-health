interface TransacaoClassificada {
  descricao: string;
  valor: number;
  categoria: string;
}

interface TabelaClassificacaoProps {
  transacoes: TransacaoClassificada[];
}

export default function TabelaClassificacao({ transacoes }: TabelaClassificacaoProps) {
  return (
    <table style={{ width: "100%", borderCollapse: "collapse" }}>
      <thead>
        <tr>
          <th style={thStyle}>Descrição</th>
          <th style={thStyle}>Valor</th>
          <th style={thStyle}>Categoria</th>
        </tr>
      </thead>
      <tbody>
        {transacoes.map((t, i) => (
          <tr key={i}>
            <td style={tdStyle}>{t.descricao}</td>
            <td style={tdStyle}>{t.valor.toFixed(2)}</td>
            <td style={tdStyle}>{t.categoria}</td>
          </tr>
        ))}
      </tbody>
    </table>
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
