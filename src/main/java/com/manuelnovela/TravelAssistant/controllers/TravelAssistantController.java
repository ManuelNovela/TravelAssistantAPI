package com.manuelnovela.TravelAssistant.controllers;

import com.manuelnovela.TravelAssistant.dtos.RegisterRequestDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.infrastructure.validator.interfaces.ValidCountryCode;
import com.manuelnovela.TravelAssistant.repositories.services.TravelAssistantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
public class TravelAssistantController {
    @Autowired
    private TravelAssistantService travelAssistantService;

    @GetMapping("/weather/{city}")
    public String getWeather(@PathVariable String city) {
        return "Previsão do tempo para " + city;
    }

    @GetMapping("/exchange-rate/{base_currency}/{target_currency}")
    public String getExchangeRate(@PathVariable String base_currency, @PathVariable String target_currency) {
        return "Taxa de câmbio entre " + base_currency + " e " + target_currency;
    }

<<<<<<< HEAD
    @GetMapping("/population/{country}")
    public ResponseEntity<RestResponseDTO> listCountryPopulation(@PathVariable @ValidCountryCode String country) {
        return travelAssistantService.listCountryPopulation(country);
    }
=======
    @GetMapping("/population/{city}")
    public String getPopulation(@PathVariable String city) {
        return "População de " + city;
    }

>>>>>>> feature/authentication
    @GetMapping("/gdp/{country}")
    public ResponseEntity<RestResponseDTO> listCountryGDP(@PathVariable @ValidCountryCode String country) {
        return travelAssistantService.listCountryGDP(country);
    }
}