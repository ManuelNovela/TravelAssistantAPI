package com.manuelnovela.TravelAssistant.dtos;

import java.util.Map;

public record ExchangeRateModelDTO(String baseCurrency, String date, double rateAmount) {
}
