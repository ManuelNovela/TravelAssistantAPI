package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataModel {
    @JsonProperty("indicator")
    private Indicator indicator;

    @JsonProperty("country")
    private Country country;

    @JsonProperty("countryiso3code")
    private String countryIso3Code;

    @JsonProperty("date")
    private String date;

    @JsonProperty("value")
    private String value;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("obs_status")
    private String obsStatus;

    @JsonProperty("decimal")
    private int decimal;
}
