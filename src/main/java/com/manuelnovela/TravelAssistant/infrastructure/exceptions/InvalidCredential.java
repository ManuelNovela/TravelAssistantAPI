package com.manuelnovela.TravelAssistant.infrastructure.exceptions;

public class InvalidCredential extends  RuntimeException{
    public InvalidCredential() {super("Invalid Credential");}
    public InvalidCredential(String message) {super(message);}

}
