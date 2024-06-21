package com.manuelnovela.TravelAssistant.infrastructure.security;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {


    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("exchangeRatesCache", "populationCache", "gdpCache", "weatherCache", "forecastCache");

        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime tomorrowMidnight = LocalDateTime.now().plusDays(1).with(midnight);
        long secondsUntilEndOfDay = LocalDateTime.now().until(tomorrowMidnight, ChronoUnit.SECONDS);

        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(100);

        cacheManager.setCaffeine(caffeineBuilder);

        return cacheManager;
    }


}
