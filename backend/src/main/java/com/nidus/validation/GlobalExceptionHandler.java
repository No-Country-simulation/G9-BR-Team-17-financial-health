package com.nidus.validation;

import com.nidus.dto.ErroResponse;
import com.nidus.service.MlServiceIndisponivelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidation(MethodArgumentNotValidException ex) {
        var fieldError = ex.getBindingResult().getFieldErrors().stream().findFirst();
        if (fieldError.isPresent()) {
            var fe = fieldError.get();
            var codigo = "CAMPO_INVALIDO";
            if (fe.getField().contains("transacoes") && fe.getCode() != null
                && fe.getCode().contains("NotEmpty")) {
                codigo = "LISTA_TRANSACOES_VAZIA";
            }
            if ("transacoes[].valor".equals(fe.getField())
                || "transacoes[].descricao".equals(fe.getField())) {
                codigo = "VALOR_TRANSACAO_INVALIDO";
            }
            return ResponseEntity.unprocessableEntity()
                .body(new ErroResponse(codigo, fe.getDefaultMessage(), fe.getField()));
        }
        return ResponseEntity.unprocessableEntity()
            .body(new ErroResponse("ERRO_VALIDACAO", "Dados invalidos", null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> handleJsonMalformado(HttpMessageNotReadableException ex) {
        var cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife) {
            var fieldName = ife.getPath().stream()
                .map(ref -> ref.getFieldName())
                .reduce((a, b) -> b)
                .orElse(null);
            return ResponseEntity.unprocessableEntity()
                .body(new ErroResponse("ENUM_INVALIDO",
                    "Valor invalido para o campo: " + fieldName, fieldName));
        }
        return ResponseEntity.badRequest()
            .body(new ErroResponse("JSON_MALFORMADO",
                "O corpo da requisicao nao e um JSON valido", null));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErroResponse> handleHttpClientError(HttpClientErrorException ex) {
        return ResponseEntity.status(ex.getStatusCode())
            .body(new ErroResponse("ERRO_ML_SERVICE", "Erro no servico de ML", null));
    }

    @ExceptionHandler(MlServiceIndisponivelException.class)
    public ResponseEntity<ErroResponse> handleMlTimeout(MlServiceIndisponivelException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
            .body(new ErroResponse("SERVICO_ML_INDISPONIVEL", ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErroResponse("FALHA_INTERNA_PROCESSAMENTO",
                "Erro inesperado durante o processamento", null));
    }
}
