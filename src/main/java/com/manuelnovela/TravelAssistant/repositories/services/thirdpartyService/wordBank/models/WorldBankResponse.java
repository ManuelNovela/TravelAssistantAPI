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
public class WorldBankResponse {
    @JsonProperty("page")
    private int page;

    @JsonProperty("pages")
    private int pages;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("sourceid")
    private String sourceId;

    @JsonProperty("lastupdated")
    private String lastUpdated;
}
