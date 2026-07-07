import logging
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import JSONResponse
from fastapi.exceptions import RequestValidationError
from pydantic import BaseModel, Field
from typing import List, Optional

from predictor import carregar_modelos, classificar_transacoes, classificar_perfil

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

modelos_carregados = False


@asynccontextmanager
async def lifespan(app: FastAPI):
    global modelos_carregados
    carregar_modelos()
    modelos_carregados = True
    yield


app = FastAPI(title="Nidus ML Service", lifespan=lifespan)


class Transacao(BaseModel):
    descricao: str = Field(..., min_length=1, max_length=120)
    valor: float = Field(..., gt=0)


class AnaliseRequest(BaseModel):
    renda_mensal: float = Field(..., gt=0)
    nivel_endividamento: float = Field(..., ge=0, le=100)
    frequencia_poupanca: str = Field(...)
    transacoes: List[Transacao] = Field(..., min_length=1)


class TransacaoClassificada(BaseModel):
    descricao: str
    valor: float
    categoria: str


class AnaliseResponse(BaseModel):
    perfil_financeiro: str
    probabilidade: float
    transacoes_classificadas: List[TransacaoClassificada]


class ErroDetail(BaseModel):
    codigo: str
    mensagem: str
    campo: Optional[str] = None
    timestamp: str


class ErroResponse(BaseModel):
    erro: ErroDetail


@app.get("/ml/health")
def health():
    if modelos_carregados:
        return {"status": "ok"}
    return JSONResponse(status_code=503, content={"status": "loading"})


@app.post("/ml/analise", response_model=AnaliseResponse)
def analise(request: AnaliseRequest):
    if request.frequencia_poupanca not in ("Nenhuma", "Baixa", "Media", "Alta"):
        raise HTTPException(
            status_code=422,
            detail={
                "codigo": "ENUM_INVALIDO",
                "mensagem": "Frequencia de poupanca invalida",
                "campo": "frequencia_poupanca",
            },
        )

    transacoes_dict = [t.model_dump() for t in request.transacoes]

    classificadas = classificar_transacoes(transacoes_dict)

    perfil, prob = classificar_perfil(
        request.renda_mensal,
        request.nivel_endividamento,
        request.frequencia_poupanca,
        classificadas,
    )

    return AnaliseResponse(
        perfil_financeiro=perfil,
        probabilidade=prob,
        transacoes_classificadas=[
            TransacaoClassificada(**t) for t in classificadas
        ],
    )


@app.exception_handler(RequestValidationError)
async def validation_exception_handler(request: Request, exc: RequestValidationError):
    errors = exc.errors()
    if not errors:
        return JSONResponse(status_code=422, content={
            "erro": {
                "codigo": "ERRO_VALIDACAO",
                "mensagem": "Dados invalidos",
                "campo": None,
                "timestamp": None,
            }
        })

    first = errors[0]
    loc = first.get("loc", [])
    field = ".".join(str(p) for p in loc[1:]) if len(loc) > 1 else (loc[0] if loc else None)
    msg = first.get("msg", "Dado invalido")

    codigo = "CAMPO_INVALIDO"
    if field and "transacoes" in field and ("too_short" in str(first.get("type", "")) or "min_length" in str(first.get("type", ""))):
        codigo = "LISTA_TRANSACOES_VAZIA"
        msg = "E necessario informar ao menos uma transacao para classificacao"
    elif field and "valor" in field and "greater than" in msg.lower():
        codigo = "VALOR_TRANSACAO_INVALIDO"
        msg = "O campo 'valor' da transacao deve ser maior que zero"
    elif field and "renda_mensal" in field:
        codigo = "CAMPO_INVALIDO"
        msg = "A renda mensal deve ser maior que zero"

    return JSONResponse(status_code=422, content={
        "erro": {
            "codigo": codigo,
            "mensagem": msg,
            "campo": field,
            "timestamp": None,
        }
    })


@app.exception_handler(HTTPException)
async def http_exception_handler(request: Request, exc: HTTPException):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "erro": exc.detail
        },
    )


@app.exception_handler(Exception)
async def generic_exception_handler(request: Request, exc: Exception):
    logger.error("Erro interno: %s", exc)
    return JSONResponse(
        status_code=500,
        content={
            "erro": {
                "codigo": "FALHA_INTERNA_PROCESSAMENTO",
                "mensagem": "Erro inesperado no servico de ML",
                "campo": None,
                "timestamp": None,
            }
        },
    )
