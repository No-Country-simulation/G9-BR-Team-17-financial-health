import { useState } from "react";
import type { Transacao } from "../types";
import type { ClassificacaoTransacoesResponse } from "../types";
import { classificarTransacoes } from "../services/api";
import FormTransacoes from "../components/FormTransacoes";
import TabelaClassificacao from "../components/TabelaClassificacao";
import ErrorAlert from "../components/ErrorAlert";

export default function ClassificacaoTransacoes() {
  const [transacoes, setTransacoes] = useState<Transacao[]>([]);
  const [resultado, setResultado] = useState<ClassificacaoTransacoesResponse | null>(null);
  const [erro, setErro] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setErro("");
    setResultado(null);
    setLoading(true);

    try {
      const data = await classificarTransacoes({ transacoes });
      setResultado(data);
    } catch (err: unknown) {
      const e = err as { erro?: { mensagem?: string; codigo?: string } };
      setErro(e?.erro?.mensagem || e?.erro?.codigo || "Erro ao classificar transações");
    } finally {
      setLoading(false);
    }
  }

  function novaClassificacao() {
    setResultado(null);
    setErro("");
    setTransacoes([]);
  }

  if (resultado) {
    return (
      <div>
        <h2>Classificação de Transações</h2>
        <TabelaClassificacao transacoes={resultado.transacoesClassificadas} />
        <button onClick={novaClassificacao} style={{ marginTop: "1rem", padding: "0.5rem 1rem" }}>
          Nova Classificação
        </button>
      </div>
    );
  }

  return (
    <div>
      <h2>Classificação de Transações</h2>
      {erro && <ErrorAlert mensagem={erro} />}
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "1rem" }}>
          <label>Transações</label>
          <FormTransacoes transacoes={transacoes} onChange={setTransacoes} />
        </div>
        <button type="submit" disabled={loading} style={{ padding: "0.5rem 2rem" }}>
          {loading ? "Classificando..." : "Classificar"}
        </button>
      </form>
    </div>
  );
}
