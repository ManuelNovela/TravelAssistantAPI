package com.manuelnovela.TravelAssistant.controllers;

import com.manuelnovela.TravelAssistant.dtos.PlaceAutocompleteResponseDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.dtos.WeatherDTO;
import com.manuelnovela.TravelAssistant.infrastructure.validator.interfaces.CurrencyCode;
import com.manuelnovela.TravelAssistant.infrastructure.validator.interfaces.ValidCountryCode;
import com.manuelnovela.TravelAssistant.repositories.services.TravelAssistantService;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate.ExchangeRateService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
public class TravelAssistantController {
    @Autowired
    private TravelAssistantService travelAssistantService;

    @RateLimiter(name = "default")
    @GetMapping("/weather/current/{city}")
    @Cacheable(value = "weatherCache", key = "#city", unless = "#result == null")
    public ResponseEntity<RestResponseDTO> getCurrentWeather(@PathVariable String city) {
        return travelAssistantService.getCurrentWeather(city);
    }

    @GetMapping("/weather/forecast/{cityId}")
    @Cacheable(value = "forecastCache", key = "#cityId", unless = "#result == null")
    public ResponseEntity<RestResponseDTO> getWeatherForecast(@PathVariable String cityId) {
        return travelAssistantService.getWeatherForecast(cityId);
    }

    @GetMapping("/exchangerate/{base_currency}/{target_currency}")
    @Cacheable(value = "exchangeRatesCache", key = "#base_currency + '-' + #target_currency", unless = "#result == null")
    public ResponseEntity<RestResponseDTO> getExchangeRate(@PathVariable @CurrencyCode String base_currency, @PathVariable @CurrencyCode String target_currency) {
        return travelAssistantService.getExchangeRates(base_currency, target_currency);
    }

    @GetMapping("/population/{country}")
    @Cacheable(value = "populationCache", key = "#country", unless = "#result == null")
    public ResponseEntity<RestResponseDTO> listCountryPopulation(@PathVariable @ValidCountryCode String country) {
        return travelAssistantService.listCountryPopulation(country);
    }

    @GetMapping("/gdp/{country}")
    @Cacheable(value = "gdpCache", key = "#country", unless = "#result == null")
    public ResponseEntity<RestResponseDTO> listCountryGDP(@PathVariable @ValidCountryCode String country) {
        return travelAssistantService.listCountryGDP(country);
    }


    @GetMapping("/autocomplete/{name}")
    @Cacheable(value = "autocompleteCache", key = "#name", unless = "#result == null")
    public ResponseEntity<RestResponseDTO> predictPlaces(@PathVariable String name) {
        return travelAssistantService.predictPlaces(name);
    }
}