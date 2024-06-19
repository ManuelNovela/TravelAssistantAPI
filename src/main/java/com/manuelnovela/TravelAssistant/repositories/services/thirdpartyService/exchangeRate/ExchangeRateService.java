package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuelnovela.TravelAssistant.dtos.ExchangeRateModelDTO;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate.models.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateService {

    @Value("${exchange.rate.api.url}")
    private String EXCHANGE_RATE_API_URL;

    @Value("${exchange.rate.api.key}")
    private String apiKey;

    public ExchangeRateModelDTO getExchangeRates(String base_Currency, String target_currency) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        String url = EXCHANGE_RATE_API_URL + apiKey;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity.getBody() != null) {
            String responseBody = responseEntity.getBody();
            ExchangeRateResponse exchangeRateResponse;
            try {
                exchangeRateResponse = objectMapper.readValue(responseBody, ExchangeRateResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            double baseToEuroRate = 1 / exchangeRateResponse.getRates().get(base_Currency);
            double targetRate = exchangeRateResponse.getRates().get(target_currency);
            double rateAmount = baseToEuroRate * targetRate;

            return new ExchangeRateModelDTO(base_Currency, target_currency,exchangeRateResponse.getDate(), rateAmount);
        } else {
            throw new RuntimeException("Failed to retrieve exchange rates");
        }
    }

}
