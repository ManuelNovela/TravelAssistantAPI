package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateError {
    private int code;
    private String type;
    private String info;
}