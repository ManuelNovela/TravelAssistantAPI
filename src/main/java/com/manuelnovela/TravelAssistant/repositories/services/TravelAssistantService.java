package com.manuelnovela.TravelAssistant.repositories.services;

import com.manuelnovela.TravelAssistant.dtos.*;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.UserAlreadyExists;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.GdpIndicator;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.PopulationIndicator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelAssistantService extends BaseService{
    private final GdpIndicator gdpIndicator;
    private final PopulationIndicator populationIndicator;
    public ResponseEntity<RestResponseDTO> listCountryGDP(String country){
        List<GdpModelDTO> result =  gdpIndicator.listCountryGDP(country);
        if(!result.isEmpty()){
            return ResponseEntity.ok(createSucessResponse(result));
        }else{
            throw new RuntimeException("Erro fetching GDP");
        }
    }
    public ResponseEntity<RestResponseDTO> listCountryPopulation(String country){
        List<PopulationModelDTO> result =  populationIndicator.listCountryPopulation(country);
        if(!result.isEmpty()){
            return ResponseEntity.ok(createSucessResponse(result));
        }else{
            throw new RuntimeException("Erro fetching GDP");
        }
    }

}
