package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuelnovela.TravelAssistant.dtos.GdpModelDTO;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.models.DataModel;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.wordBank.models.WorldBankResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GdpIndicator {
    @Value("${world.bank.gdp.api.url}")
    private String WORLD_BANK_API_URL;
    public List<GdpModelDTO> listCountryGDP(String countryCode) {

        List<GdpModelDTO> allGDPData = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();


        int currentPage = 1;
        while (true) {

            String url = WORLD_BANK_API_URL.replace("{countryCode}", countryCode) + "&page=" + currentPage;
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (responseEntity.getBody() != null) {
                String responseBody = responseEntity.getBody();
                Object[] response = new Object[0];
                try {
                    response = objectMapper.readValue(responseBody, Object[].class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                if (response.length > 1) {
                    WorldBankResponse paginationInfo = objectMapper.convertValue(response[0], WorldBankResponse.class);
                    List<DataModel> gdpDataList = objectMapper.convertValue(response[1], objectMapper.getTypeFactory().constructCollectionType(List.class, DataModel.class));

                    for (DataModel gdpData : gdpDataList) {
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