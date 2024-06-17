package com.manuelnovela.TravelAssistant.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
    @Valid

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    String email,

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password) {
}
