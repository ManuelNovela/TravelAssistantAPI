package com.manuelnovela.TravelAssistant.infrastructure.handler;

import com.manuelnovela.TravelAssistant.dtos.ApiStatus;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.RateLimitExceededException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponseDTO<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";
        RestResponseDTO<?> response = new RestResponseDTO<>(ApiStatus.ERROR, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RestResponseDTO<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        RestResponseDTO<?> responseDTO = new RestResponseDTO<>(ApiStatus.ERROR, "Required request body is missing: " + ex.getParameterName());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestResponseDTO> runtimeExceptionHandler(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body( new RestResponseDTO(ApiStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<String> handleRateLimitExceededException(RateLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponseDTO<?>> handleGeneralException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RestResponseDTO<>(ApiStatus.ERROR, exception.getMessage()));
    }
}
