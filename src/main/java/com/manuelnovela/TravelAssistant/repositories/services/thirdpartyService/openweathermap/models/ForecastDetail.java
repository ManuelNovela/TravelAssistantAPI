package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models;

import lombok.Data;

import java.util.List;
@Data
public class ForecastDetail {
    private long dt;
    private Root main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private int visibility;
    private double pop;
    private Sys sys;
    private String dt_txt;
}
