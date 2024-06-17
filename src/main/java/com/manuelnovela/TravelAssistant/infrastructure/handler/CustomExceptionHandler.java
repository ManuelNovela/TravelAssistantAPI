package com.manuelnovela.TravelAssistant.infrastructure.handler;

import com.manuelnovela.TravelAssistant.dtos.ApiStatus;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.InvalidCredential;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.UserAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;




@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserAlreadyExists.class)
    private ResponseEntity<RestResponseDTO> userAlreadyExistsHandler(UserAlreadyExists exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body( new RestResponseDTO(ApiStatus.ERROR, exception.getMessage()));
    }
    @ExceptionHandler(InvalidCredential.class)
    private ResponseEntity<RestResponseDTO> invalidCredentialHandler(InvalidCredential exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body( new RestResponseDTO(ApiStatus.ERROR, exception.getMessage()));
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<RestResponseDTO> usernameNotFoundExceptionHandler(UsernameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new RestResponseDTO(ApiStatus.ERROR, exception.getMessage()));
    }



}
