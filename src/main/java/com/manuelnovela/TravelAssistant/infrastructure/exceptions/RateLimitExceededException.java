package com.manuelnovela.TravelAssistant.infrastructure.exceptions;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}