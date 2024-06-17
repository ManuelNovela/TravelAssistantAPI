package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models;

import lombok.Data;

import java.util.List;
@Data
public class ForecastResponse {
    private String cod;
    private int message;
    private int cnt;
    private List<ForecastDetail> list;
    private City city;
}
