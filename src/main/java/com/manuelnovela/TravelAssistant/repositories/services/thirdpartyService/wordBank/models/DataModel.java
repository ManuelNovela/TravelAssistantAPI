package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private BigDecimal value;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("obs_status")
    private String obsStatus;

    @JsonProperty("decimal")
    private int decimal;
}
