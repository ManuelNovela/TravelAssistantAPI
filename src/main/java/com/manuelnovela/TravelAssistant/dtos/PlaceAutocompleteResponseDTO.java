package com.manuelnovela.TravelAssistant.dtos;

import java.util.List;

public record PlaceAutocompleteResponseDTO (List<PredictionDTO> predictions){
}