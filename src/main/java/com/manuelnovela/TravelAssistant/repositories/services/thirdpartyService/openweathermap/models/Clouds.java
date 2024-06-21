package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clouds {
    private int all;
}
