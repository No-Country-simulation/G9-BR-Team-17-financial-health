import { useState } from "react";
import type { Transacao } from "../types";

interface FormTransacoesProps {
  transacoes: Transacao[];
  onChange: (transacoes: Transacao[]) => void;
}

export default function FormTransacoes({ transacoes, onChange }: FormTransacoesProps) {
  const [descricao, setDescricao] = useState("");
  const [valor, setValor] = useState("");

  function adicionar() {
    if (!descricao.trim()) return;
    const v = parseFloat(valor);
    if (isNaN(v) || v <= 0) return;
    onChange([...transacoes, { descricao: descricao.trim(), valor: v }]);
    setDescricao("");
    setValor("");
  }

  function remover(index: number) {
    onChange(transacoes.filter((_, i) => i !== index));
  }

  return (
    <div>
      <div style={{ display: "flex", gap: "0.5rem", marginBottom: "0.5rem" }}>
        <input
          placeholder="Descrição"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          style={{ flex: 1, padding: "0.5rem" }}
        />
        <input
          placeholder="Valor"
          type="number"
          min="0.01"
          step="0.01"
          value={valor}
          onChange={(e) => setValor(e.target.value)}
          style={{ width: "120px", padding: "0.5rem" }}
        />
        <button type="button" onClick={adicionar} style={{ padding: "0.5rem 1rem" }}>
          Adicionar
        </button>
      </div>
      {transacoes.length > 0 && (
        <table style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th style={thStyle}>Descrição</th>
              <th style={thStyle}>Valor</th>
              <th style={{ ...thStyle, width: "50px" }}></th>
            </tr>
          </thead>
          <tbody>
            {transacoes.map((t, i) => (
              <tr key={i}>
                <td style={tdStyle}>{t.descricao}</td>
                <td style={tdStyle}>{t.valor.toFixed(2)}</td>
                <td style={tdStyle}>
                  <button type="button" onClick={() => remover(i)} style={{ color: "red", cursor: "pointer", border: "none", background: "none" }}>
                    ✕
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
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
