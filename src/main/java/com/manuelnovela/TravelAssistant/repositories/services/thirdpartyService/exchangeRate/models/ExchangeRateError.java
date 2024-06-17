package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate.models;

import lombok.Data;

@Data
public class ExchangeRateError {
    private int code;
    private String type;
    private String info;
}