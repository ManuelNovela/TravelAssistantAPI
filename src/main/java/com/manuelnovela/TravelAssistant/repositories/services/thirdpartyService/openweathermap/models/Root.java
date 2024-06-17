package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models;

import lombok.Data;
@Data
public class Root {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;
}
