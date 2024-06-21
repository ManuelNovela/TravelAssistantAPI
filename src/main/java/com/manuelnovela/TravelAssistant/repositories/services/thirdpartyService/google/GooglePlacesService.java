package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuelnovela.TravelAssistant.dtos.ApiStatus;
import com.manuelnovela.TravelAssistant.dtos.PlaceAutocompleteResponseDTO;
import com.manuelnovela.TravelAssistant.dtos.PredictionDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.google.models.GooglePlacesModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GooglePlacesService {

    @Value("${google.places.api.key}")
    private String googleApiKey;

    @Value("${google.places.api.url}")
    private String GOOGLE_PLACES_API_URL;

    public RestResponseDTO<PlaceAutocompleteResponseDTO> getAutocompleteSuggestions(String input) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        String url = GOOGLE_PLACES_API_URL.replace("{input}", input).replace("{key}", googleApiKey);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                GooglePlacesModel googlePlacesModel = objectMapper.readValue(responseBody, GooglePlacesModel.class);

                List<PredictionDTO> predictions = googlePlacesModel.getPredictions().stream()
                        .map(prediction -> new PredictionDTO(prediction.getDescription(), prediction.getPlaceId()))
                        .collect(Collectors.toList());

                PlaceAutocompleteResponseDTO placeAutocompleteResponseDTO = new PlaceAutocompleteResponseDTO(predictions);

                return new RestResponseDTO<>(ApiStatus.SUCCESS, "Autocomplete suggestions retrieved successfully", placeAutocompleteResponseDTO);
            } else {
                return new RestResponseDTO<>(ApiStatus.ERROR, "Failed to retrieve autocomplete suggestions");
            }
        } catch (JsonProcessingException e) {
            return new RestResponseDTO<>(ApiStatus.ERROR, "Error processing JSON response: " + e.getMessage());
        } catch (Exception e) {
            return new RestResponseDTO<>(ApiStatus.ERROR, "Error retrieving autocomplete suggestions: " + e.getMessage());
        }
    }
}
