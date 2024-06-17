package com.manuelnovela.TravelAssistant.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotEmpty(message = "Username cannot be empty")
        @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
        String username,

        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password){
}
