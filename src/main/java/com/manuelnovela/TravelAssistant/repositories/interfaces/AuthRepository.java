package com.manuelnovela.TravelAssistant.repositories.interfaces;

import com.manuelnovela.TravelAssistant.dtos.LoginRequestDTO;
import com.manuelnovela.TravelAssistant.dtos.RegisterRequestDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import org.springframework.http.ResponseEntity;


public interface AuthRepository{
    ResponseEntity<RestResponseDTO> login (LoginRequestDTO body);
    ResponseEntity<RestResponseDTO> register (RegisterRequestDTO body);
}
