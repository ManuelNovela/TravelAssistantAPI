package com.manuelnovela.TravelAssistant.controllers;

import com.manuelnovela.TravelAssistant.dtos.*;
import com.manuelnovela.TravelAssistant.repositories.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController{
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RestResponseDTO>  login (@Valid @RequestBody LoginRequestDTO body){
        return authService.login(body);
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponseDTO> register (@Valid @RequestBody RegisterRequestDTO body){
        return authService.register(body);
    }

}
