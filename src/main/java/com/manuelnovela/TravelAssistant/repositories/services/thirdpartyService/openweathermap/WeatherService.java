package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuelnovela.TravelAssistant.dtos.*;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models.CurrentWeatherResponse;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models.ForecastDetail;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.openweathermap.models.ForecastResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${openweathermap.current.api.url}")
    private String CURRENT_WEATHER_URL;
    @Value("${openweathermap.forecast.api.url}")
    private String FORECAST_URL;
    @Value("${openweathermap.api.key}")
    private String apiKey = "ecd54ccd2985aca1aea0251dcdcc37b3";

    public RestResponseDTO<WeatherDTO> getCurrentWeather(String city) {

        String url = CURRENT_WEATHER_URL.replace("{city}", city).replace("{apikey}", apiKey);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CurrentWeatherResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, CurrentWeatherResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                CurrentWeatherResponse response = responseEntity.getBody();

                String description = response.getWeather().get(0).getDescription();
                double temperature = response.getMain().getTemp();
                String cityName = response.getName();
                Integer cityId = response.getId();

                WeatherDTO weatherDTO = new WeatherDTO(cityId,cityName, temperature, description);
                return new RestResponseDTO<>(ApiStatus.SUCCESS, "Current weather retrieved successfully", weatherDTO);
            } else {
                return new RestResponseDTO<>(ApiStatus.ERROR, "Failed to retrieve current weather");
            }
        } catch (Exception e) {
            return new RestResponseDTO<>(ApiStatus.ERROR, "Error retrieving current weather: " + e.getMessage());
        }
    }

    public RestResponseDTO<ForecastDTO> getWeatherForecast(String cityId) {

        String url = FORECAST_URL.replace("{cityId}", cityId).replace("{apikey}", apiKey);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ForecastResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, ForecastResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ForecastResponse response = responseEntity.getBody();
                String cityName = response.getCity().getName();

                List<ForecastDetail> list = response.getList();
                List<ForecastDetailDTO> forecastDetails = new ArrayList<>();
                for (ForecastDetail item : list) {
                    String dateTime = item.getDt_txt();
                    double temperature = item.getMain().getTemp();
                    String description = item.getWeather().get(0).getDescription();
                    forecastDetails.add(new ForecastDetailDTO(dateTime, temperature, description));
                }

                ForecastDTO forecastDTO = new ForecastDTO(cityName, forecastDetails);
                return new RestResponseDTO<>(ApiStatus.SUCCESS, "Weather forecast retrieved successfully", forecastDTO);
            } else {
                return new RestResponseDTO<>(ApiStatus.ERROR, "Failed to retrieve weather forecast");
            }
        } catch (Exception e) {
            return new RestResponseDTO<>(ApiStatus.ERROR, "Error retrieving weather forecast: " + e.getMessage());
        }
    }
}
