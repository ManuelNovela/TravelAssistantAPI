package com.manuelnovela.TravelAssistant.infrastructure.exceptions;

public class UserAlreadyExists extends  RuntimeException{
    public UserAlreadyExists() {super("User Already Exists!");}
    public UserAlreadyExists(String message) {super(message);}

}