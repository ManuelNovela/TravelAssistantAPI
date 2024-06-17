package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models;

import lombok.Data;

@Data
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
