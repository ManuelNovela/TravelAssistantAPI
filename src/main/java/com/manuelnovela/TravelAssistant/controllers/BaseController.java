package com.manuelnovela.TravelAssistant.controllers;

import com.manuelnovela.TravelAssistant.dtos.ApiStatus;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;

public class BaseController<T> {

    public RestResponseDTO<T> createSucessResponse(T data)
    {
        RestResponseDTO result = new RestResponseDTO(ApiStatus.SUCCESS);
        result.setData(data);
        return result;
    }

<<<<<<< Updated upstream:src/main/java/com/manuelnovela/TravelAssistant/controllers/BaseController.java
    public RestResponseDTO<T> createErrorResponse(T data)
    {
        RestResponseDTO result = new RestResponseDTO(ApiStatus.ERROR);
        result.setData(data);
        return result;
    }
=======
>>>>>>> Stashed changes:src/main/java/com/manuelnovela/TravelAssistant/repositories/services/BaseService.java
}
