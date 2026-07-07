import { render, screen } from "@testing-library/react";
import { describe, it, expect } from "vitest";
import ErrorAlert from "./ErrorAlert";

describe("ErrorAlert", () => {
  it("renderiza mensagem de erro", () => {
    render(<ErrorAlert mensagem="Erro ao processar análise" />);
    expect(screen.getByText("Erro ao processar análise")).toBeInTheDocument();
  });
});
