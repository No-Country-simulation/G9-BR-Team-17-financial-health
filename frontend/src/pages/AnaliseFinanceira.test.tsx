import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { BrowserRouter } from "react-router-dom";
import { describe, it, expect } from "vitest";
import AnaliseFinanceira from "./AnaliseFinanceira";

function renderPage() {
  return render(
    <BrowserRouter>
      <AnaliseFinanceira />
    </BrowserRouter>
  );
}

describe("AnaliseFinanceira", () => {
  it("renderiza formulario inicial", () => {
    renderPage();
    expect(screen.getByText("Análise Financeira")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Analisar" })).toBeInTheDocument();
  });

  it("exibe resultado apos submit com dados validos", async () => {
    renderPage();
    const user = userEvent.setup();

    const rendaInput = screen.getAllByRole("spinbutton")[0];
    await user.type(rendaInput, "5000");
    await user.click(screen.getByRole("button", { name: "Analisar" }));

    await waitFor(() => {
      expect(screen.getByText("Resultado da Análise")).toBeInTheDocument();
    });

    expect(screen.getByTestId("card-perfil")).toBeInTheDocument();
  });
});
