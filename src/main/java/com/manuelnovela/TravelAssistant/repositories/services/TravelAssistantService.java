package com.manuelnovela.TravelAssistant.repositories.services;

import com.manuelnovela.TravelAssistant.dtos.ApiStatus;
import com.manuelnovela.TravelAssistant.dtos.GdpModelDTO;
import com.manuelnovela.TravelAssistant.dtos.LoginResponseDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.UserAlreadyExists;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.GdpIndicator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelAssistantService extends BaseService{
    private final GdpIndicator gdpIndicator;
    public ResponseEntity<RestResponseDTO> listCountryGDP(String country){

        List<GdpModelDTO> result =  gdpIndicator.listCountryGDP(country);

        if(!result.isEmpty()){
            return ResponseEntity.ok(createSucessResponse(result));
        }else{
            throw new RuntimeException("Erro fetching GDP");
        }

    }
}
