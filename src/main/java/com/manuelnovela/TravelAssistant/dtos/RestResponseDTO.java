package com.manuelnovela.TravelAssistant.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestResponseDTO<T> {

    private ApiStatus status;
    private String message;
    private T data;

    public RestResponseDTO(ApiStatus status){
        this.status = status;
    }
    public RestResponseDTO(ApiStatus status, String message){
        this.status = status;
        this.message = message;
    }
}




