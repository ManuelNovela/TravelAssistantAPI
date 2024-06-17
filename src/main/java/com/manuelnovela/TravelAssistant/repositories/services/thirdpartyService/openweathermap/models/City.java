package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models;

import lombok.Data;

@Data
public class City {
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;
}
