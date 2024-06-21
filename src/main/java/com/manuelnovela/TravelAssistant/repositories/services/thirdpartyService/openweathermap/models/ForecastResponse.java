package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    private String cod;
    private int message;
    private int cnt;
    private List<ForecastDetail> list;
    private City city;
}
