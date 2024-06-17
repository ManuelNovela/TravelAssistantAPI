package com.manuelnovela.TravelAssistant.repositories.services;

import com.manuelnovela.TravelAssistant.dtos.ApiStatus;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;

public class BaseService<T> {

    public RestResponseDTO<T> createSucessResponse(T data)
    {
        RestResponseDTO result = new RestResponseDTO(ApiStatus.SUCCESS);
        result.setData(data);
        return result;
    }

    public RestResponseDTO<T> createErrorResponse(T data)
    {
        RestResponseDTO result = new RestResponseDTO(ApiStatus.ERROR);
        result.setData(data);
        return result;
    }

    public RestResponseDTO<T> createErrorResponse(String mesage)
    {
        RestResponseDTO result = new RestResponseDTO(ApiStatus.ERROR);
        result.setMessage(mesage);
        return result;
    }
}
