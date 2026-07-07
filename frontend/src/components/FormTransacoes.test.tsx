import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { describe, it, expect, vi } from "vitest";
import FormTransacoes from "./FormTransacoes";

describe("FormTransacoes", () => {
  it("adiciona transacao ao clicar em Adicionar", async () => {
    const onChange = vi.fn();
    render(<FormTransacoes transacoes={[]} onChange={onChange} />);
    const user = userEvent.setup();

    await user.type(screen.getByPlaceholderText("Descrição"), "Supermercado");
    await user.type(screen.getByPlaceholderText("Valor"), "420");
    await user.click(screen.getByRole("button", { name: "Adicionar" }));

    expect(onChange).toHaveBeenCalledWith([{ descricao: "Supermercado", valor: 420 }]);
  });

  it("exibe transacoes adicionadas na tabela", () => {
    render(
      <FormTransacoes
        transacoes={[
          { descricao: "Supermercado", valor: 420 },
          { descricao: "Combustivel", valor: 300 },
        ]}
        onChange={vi.fn()}
      />
    );

    expect(screen.getByText("Supermercado")).toBeInTheDocument();
    expect(screen.getByText("420.00")).toBeInTheDocument();
    expect(screen.getByText("Combustivel")).toBeInTheDocument();
    expect(screen.getByText("300.00")).toBeInTheDocument();
  });

  it("remove transacao ao clicar no botao ✕", async () => {
    const onChange = vi.fn();
    render(
      <FormTransacoes
        transacoes={[{ descricao: "Supermercado", valor: 420 }]}
        onChange={onChange}
      />
    );
    const user = userEvent.setup();

    await user.click(screen.getByRole("button", { name: "✕" }));
    expect(onChange).toHaveBeenCalledWith([]);
  });
});
