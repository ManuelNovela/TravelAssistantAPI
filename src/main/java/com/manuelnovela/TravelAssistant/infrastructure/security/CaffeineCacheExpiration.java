package com.manuelnovela.TravelAssistant.infrastructure.security;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.AbstractCacheManager;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CaffeineCacheExpiration implements Expiry<Object, Object> {

    @Override
    public long expireAfterCreate(Object key, Object value, long currentTime) {
        if("exchangeRatesCache".equals(key)){
            return 1;
        }else if ("populationCache".equals(key)) {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime tomorrowMidnight = LocalDateTime.now().plusDays(1).with(midnight);
            long secondsUntilEndOfDay = LocalDateTime.now().until(tomorrowMidnight, ChronoUnit.SECONDS);
            return secondsUntilEndOfDay;
        } else {
            return TimeUnit.HOURS.toSeconds(1);
        }
    }

    @Override
    public long expireAfterUpdate(Object key, Object value, long currentTime, long currentDuration) {
        if("exchangeRatesCache".equals(key)){
            return 1;
        }else if ("populationCache".equals(key)) {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime tomorrowMidnight = LocalDateTime.now().plusDays(1).with(midnight);
            long secondsUntilEndOfDay = LocalDateTime.now().until(tomorrowMidnight, ChronoUnit.SECONDS);
            return secondsUntilEndOfDay;
        } else {
            return TimeUnit.HOURS.toSeconds(1);
        }
    }

    @Override
    public long expireAfterRead(Object key, Object value, long currentTime, long currentDuration) {
        if("exchangeRatesCache".equals(key)){
            return 1;
        }else if ("populationCache".equals(key)) {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime tomorrowMidnight = LocalDateTime.now().plusDays(1).with(midnight);
            long secondsUntilEndOfDay = LocalDateTime.now().until(tomorrowMidnight, ChronoUnit.SECONDS);
            return secondsUntilEndOfDay;
        } else {
            return TimeUnit.HOURS.toSeconds(1);
        }
    }
}