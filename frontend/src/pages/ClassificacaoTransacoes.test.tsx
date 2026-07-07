import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { BrowserRouter } from "react-router-dom";
import { describe, it, expect } from "vitest";
import ClassificacaoTransacoes from "./ClassificacaoTransacoes";

function renderPage() {
  return render(
    <BrowserRouter>
      <ClassificacaoTransacoes />
    </BrowserRouter>
  );
}

describe("ClassificacaoTransacoes", () => {
  it("renderiza formulario inicial", () => {
    renderPage();
    expect(screen.getByText("Classificação de Transações")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Classificar" })).toBeInTheDocument();
  });
});
