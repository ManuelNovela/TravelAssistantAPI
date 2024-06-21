package com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuelnovela.TravelAssistant.dtos.ExchangeRateModelDTO;
import com.manuelnovela.TravelAssistant.repositories.services.thirdpartyService.exchangeRate.models.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class ExchangeRateService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${exchange.rate.api.url}")
    private String EXCHANGE_RATE_API_URL;

    @Value("${exchange.rate.api.key}")
    private String apiKey;

    private ExchangeRateResponse cachedExchangeRate;
    private String cachedDate;
    private final String localDataPath = "localDataContext/exchangeData/exchangeRate.json";

    public ExchangeRateService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        loadLocalData();
    }

    public ExchangeRateModelDTO getExchangeRates(String base_Currency, String target_currency) {
        try {
            if (cachedExchangeRate == null || !isCacheValid()) {
                updateCache();
            }
            double baseToEuroRate = 1 / cachedExchangeRate.getRates().get(base_Currency);
            double targetRate = cachedExchangeRate.getRates().get(target_currency);
            double rateAmountBuy = baseToEuroRate * targetRate;

            double baseToEuroRateReversal = 1 / cachedExchangeRate.getRates().get(target_currency);
            double targetRateReversal = cachedExchangeRate.getRates().get(base_Currency);
            double rateAmountSell = baseToEuroRateReversal * targetRateReversal;

            return new ExchangeRateModelDTO(base_Currency, target_currency, cachedExchangeRate.getDate(), rateAmountBuy, rateAmountSell);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse exchange rate response", e);
        }
    }


    @CacheEvict(value = "exchangeRatesCache", allEntries = true)
    public void cleanCache() {
    }
    private boolean isCacheValid() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(!today.equals(cachedDate)){
            cleanCache();
            return false;
        }
        return true;
    }

    private void updateCache() {
        String url = EXCHANGE_RATE_API_URL + apiKey;
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        if (responseEntity.getBody() != null) {
            String responseBody = responseEntity.getBody();
            ExchangeRateResponse exchangeRateResponse;
            try {
                exchangeRateResponse = objectMapper.readValue(responseBody, ExchangeRateResponse.class);
                cachedExchangeRate = exchangeRateResponse;
                cachedDate = exchangeRateResponse.getDate();
                saveToLocalData(responseBody);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse exchange rate response", e);
            }
        } else {
            throw new RuntimeException("Failed to retrieve exchange rates");
        }
    }

    private void saveToLocalData(String responseBody) {
        try {
            Path directoryPath = Paths.get(localDataPath).getParent();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Path filePath = Paths.get(localDataPath);
            Files.write(filePath, responseBody.getBytes());

        } catch (IOException e) {
            throw new RuntimeException("Failed to save exchange rate data locally", e);
        }
    }

    public void loadLocalData() {
        try {
            File file = new File(localDataPath);
            if (file.exists()) {
                String responseBody = new String(Files.readAllBytes(file.toPath()));
                ExchangeRateResponse exchangeRateResponse = objectMapper.readValue(responseBody, ExchangeRateResponse.class);
                cachedExchangeRate = exchangeRateResponse;
                cachedDate = exchangeRateResponse.getDate();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load exchange rate data from local storage", e);
        }
    }

}
