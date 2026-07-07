interface ResultadoPerfilProps {
  perfil: string;
  probabilidade: number;
}

function getColor(perfil: string): string {
  switch (perfil) {
    case "Saudavel": return "#28a745";
    case "Em observacao": return "#ffc107";
    case "Em risco": return "#dc3545";
    default: return "#6c757d";
  }
}

export default function ResultadoPerfil({ perfil, probabilidade }: ResultadoPerfilProps) {
  return (
    <div
      data-testid="card-perfil"
      style={{
        border: `2px solid ${getColor(perfil)}`,
        borderRadius: "8px",
        padding: "1.5rem",
        textAlign: "center",
        marginBottom: "1rem",
      }}
    >
      <h2 style={{ color: getColor(perfil), margin: 0 }}>{perfil}</h2>
      <p style={{ fontSize: "1.2rem", margin: "0.5rem 0 0 0" }}>
        Confiança: {(probabilidade * 100).toFixed(0)}%
      </p>
    </div>
  );
}
