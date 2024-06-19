package com.manuelnovela.TravelAssistant.dtos;

import java.util.List;

public record WeatherDTO(String country, Integer cityId, String city, double currentTemperature, String currentDescription) {
}
