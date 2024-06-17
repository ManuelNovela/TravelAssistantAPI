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
public class Indicator {
    @JsonProperty("id")
    private String id;

    @JsonProperty("value")
    private String value;
}
