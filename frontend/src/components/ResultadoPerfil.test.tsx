import { render, screen } from "@testing-library/react";
import { describe, it, expect } from "vitest";
import ResultadoPerfil from "./ResultadoPerfil";

describe("ResultadoPerfil", () => {
  it("renderiza perfil Saudavel", () => {
    render(<ResultadoPerfil perfil="Saudavel" probabilidade={0.85} />);
    expect(screen.getByText("Saudavel")).toBeInTheDocument();
    expect(screen.getByText("Confiança: 85%")).toBeInTheDocument();
  });

  it("renderiza perfil Em observacao", () => {
    render(<ResultadoPerfil perfil="Em observacao" probabilidade={0.5} />);
    expect(screen.getByText("Em observacao")).toBeInTheDocument();
    expect(screen.getByText("Confiança: 50%")).toBeInTheDocument();
  });

  it("renderiza perfil Em risco", () => {
    render(<ResultadoPerfil perfil="Em risco" probabilidade={0.12} />);
    expect(screen.getByText("Em risco")).toBeInTheDocument();
    expect(screen.getByText("Confiança: 12%")).toBeInTheDocument();
  });

  it("usa data-testid card-perfil", () => {
    render(<ResultadoPerfil perfil="Saudavel" probabilidade={1} />);
    expect(screen.getByTestId("card-perfil")).toBeInTheDocument();
  });
});
