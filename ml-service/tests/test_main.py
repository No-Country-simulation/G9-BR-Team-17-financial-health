from fastapi.testclient import TestClient
from main import app

client = TestClient(app)


def test_health():
    response = client.get("/ml/health")
    assert response.status_code in (200, 503)


def test_analise_completa():
    response = client.post("/ml/analise", json={
        "renda_mensal": 8000,
        "nivel_endividamento": 5,
        "frequencia_poupanca": "Alta",
        "transacoes": [
            {"descricao": "Aluguel", "valor": 1500},
            {"descricao": "Farmacia", "valor": 120},
        ],
    })
    assert response.status_code == 200
    data = response.json()
    assert "perfil_financeiro" in data
    assert "probabilidade" in data
    assert "transacoes_classificadas" in data
    assert len(data["transacoes_classificadas"]) == 2


def test_analise_sem_transacoes():
    response = client.post("/ml/analise", json={
        "renda_mensal": 8000,
        "nivel_endividamento": 5,
        "frequencia_poupanca": "Alta",
        "transacoes": [],
    })
    assert response.status_code == 422
    data = response.json()
    assert data["erro"]["codigo"] == "LISTA_TRANSACOES_VAZIA"


def test_analise_valor_negativo():
    response = client.post("/ml/analise", json={
        "renda_mensal": 8000,
        "nivel_endividamento": 5,
        "frequencia_poupanca": "Alta",
        "transacoes": [{"descricao": "Teste", "valor": -100}],
    })
    assert response.status_code == 422
    data = response.json()
    assert "erro" in data
    assert data["erro"]["codigo"] == "VALOR_TRANSACAO_INVALIDO"


def test_frequencia_poupanca_invalida():
    response = client.post("/ml/analise", json={
        "renda_mensal": 8000,
        "nivel_endividamento": 5,
        "frequencia_poupanca": "Invalida",
        "transacoes": [{"descricao": "Teste", "valor": 100}],
    })
    assert response.status_code == 422
    data = response.json()
    assert data["erro"]["codigo"] == "ENUM_INVALIDO"
    assert "frequencia_poupanca" in data["erro"]["campo"]


def test_renda_zero():
    response = client.post("/ml/analise", json={
        "renda_mensal": 0,
        "nivel_endividamento": 5,
        "frequencia_poupanca": "Media",
        "transacoes": [{"descricao": "Teste", "valor": 100}],
    })
    assert response.status_code == 422
    data = response.json()
    assert data["erro"]["codigo"] == "CAMPO_INVALIDO"
    assert "renda mensal" in data["erro"]["mensagem"]


def test_classifica_todas_categorias():
    response = client.post("/ml/analise", json={
        "renda_mensal": 10000,
        "nivel_endividamento": 10,
        "frequencia_poupanca": "Media",
        "transacoes": [
            {"descricao": "Supermercado", "valor": 500},
            {"descricao": "Uber", "valor": 50},
            {"descricao": "Farmacia", "valor": 100},
            {"descricao": "Aluguel", "valor": 2000},
            {"descricao": "Curso", "valor": 300},
            {"descricao": "Cinema", "valor": 40},
            {"descricao": "Cartao de credito", "valor": 800},
        ],
    })
    assert response.status_code == 200
    cats = {t["categoria"] for t in response.json()["transacoes_classificadas"]}
    assert "Alimentacao" in cats
    assert "Transporte" in cats
    assert "Saude" in cats
    assert "Moradia" in cats
    assert "Educacao" in cats
    assert "Lazer" in cats
    assert "Servicos" in cats
