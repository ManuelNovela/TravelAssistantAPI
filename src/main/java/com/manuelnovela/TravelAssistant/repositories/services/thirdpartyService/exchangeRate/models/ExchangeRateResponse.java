package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {
    private boolean success;
    private ExchangeRateError error;
    private long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;
}
