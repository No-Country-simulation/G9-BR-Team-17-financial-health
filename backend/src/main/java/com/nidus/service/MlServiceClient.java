package com.nidus.service;

import com.nidus.dto.MlAnaliseRequest;
import com.nidus.dto.MlAnaliseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class MlServiceClient {

    private final RestTemplate restTemplate;
    private final String mlServiceUrl;
    private final int timeout;

    public MlServiceClient(@Value("${ml-service.url}") String mlServiceUrl,
                           @Value("${ml-service.timeout}") int timeout) {
        this.mlServiceUrl = mlServiceUrl;
        this.timeout = timeout;
        this.restTemplate = new RestTemplate();
    }

    public MlAnaliseResponse analisar(MlAnaliseRequest request) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(request, headers);

        try {
            return restTemplate.postForObject(
                mlServiceUrl + "/ml/analise", entity, MlAnaliseResponse.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (ResourceAccessException e) {
            throw new MlServiceIndisponivelException("ML Service nao respondeu dentro do limite de tempo");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar ML Service", e);
        }
    }
}
