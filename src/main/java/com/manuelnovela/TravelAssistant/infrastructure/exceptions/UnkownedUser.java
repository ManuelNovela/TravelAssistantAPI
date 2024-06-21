package com.manuelnovela.TravelAssistant.infrastructure.exceptions;

public class UnkownedUser extends RuntimeException {
    public UnkownedUser(String message) {
        super(message);
    }
}