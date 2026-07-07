import { http, HttpResponse } from "msw";
import { analiseFinanceiraMock, classificacaoMock } from "./data";

export const handlers = [
  http.post("/api/analise-financeira", async ({ request }) => {
    const body = await request.json() as { renda_mensal?: number };
    if (body.renda_mensal != null && body.renda_mensal <= 0) {
      return HttpResponse.json(
        { erro: { codigo: "CAMPO_INVALIDO", mensagem: "Renda deve ser maior que zero", campo: "renda_mensal", timestamp: new Date().toISOString() } },
        { status: 422 }
      );
    }
    return HttpResponse.json(analiseFinanceiraMock);
  }),

  http.post("/api/classificacao-transacoes", async () => {
    return HttpResponse.json(classificacaoMock);
  }),
];
