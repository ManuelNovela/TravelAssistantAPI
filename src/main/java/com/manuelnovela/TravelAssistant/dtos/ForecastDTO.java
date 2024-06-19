package com.manuelnovela.TravelAssistant.dtos;

import java.util.List;

public record ForecastDTO(String city, List<ForecastDetailDTO> forecastToday,List<ForecastDetailDTO> forecastWeek) {
}
