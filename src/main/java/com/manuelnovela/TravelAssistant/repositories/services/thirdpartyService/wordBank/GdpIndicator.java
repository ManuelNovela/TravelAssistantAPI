package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuelnovela.TravelAssistant.dtos.GdpModelDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.models.GdpData;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.models.WorldBankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GdpIndicator {
    private final String WORLD_BANK_API_URL = "http://api.worldbank.org/v2/country/{countryCode}/indicator/NY.GDP.MKTP.CD?format=json";
    public List<GdpModelDTO> listCountryGDP(String countryCode) {

        List<GdpModelDTO> allGDPData = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();


        int currentPage = 1;
        while (true) {

            String url = WORLD_BANK_API_URL.replace("{countryCode}", countryCode) + "&page=" + currentPage;
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (responseEntity != null && responseEntity.getBody() != null) {
                String responseBody = responseEntity.getBody();
                Object[] response = new Object[0];
                try {
                    response = objectMapper.readValue(responseBody, Object[].class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                if (response.length > 1) {
                    WorldBankResponse paginationInfo = objectMapper.convertValue(response[0], WorldBankResponse.class);
                    List<GdpData> gdpDataList = objectMapper.convertValue(response[1], objectMapper.getTypeFactory().constructCollectionType(List.class, GdpData.class));

                    for (GdpData gdpData : gdpDataList) {
                        GdpModelDTO gdpModel = new GdpModelDTO(gdpData.getCountry().getValue(), gdpData.getDate(), gdpData.getValue());
                        allGDPData.add(gdpModel);
                    }

                    if (currentPage >= paginationInfo.getPages()) {
                        break;
                    } else {
                        currentPage++;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        return allGDPData;

    }
}
