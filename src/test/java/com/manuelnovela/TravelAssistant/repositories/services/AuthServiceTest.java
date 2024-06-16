package com.manuelnovela.TravelAssistant.repositories.services;

import com.manuelnovela.TravelAssistant.domain.user.User;
import com.manuelnovela.TravelAssistant.dtos.LoginRequestDTO;
import com.manuelnovela.TravelAssistant.dtos.LoginResponseDTO;
import com.manuelnovela.TravelAssistant.dtos.RegisterRequestDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.InvalidCredential;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.UserAlreadyExists;
import com.manuelnovela.TravelAssistant.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void loginUserNotFound() {

        LoginRequestDTO request = new LoginRequestDTO("manuelnovela48@gmail.com", "Novela123");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        Exception thrown = assertThrows(Exception.class, () -> {
                authService.login(request);
            }
        );

        assertEquals(thrown.getMessage(), "User not found");
    }

    @Test
    @DisplayName("Should login successfully with valid credentials")
    void loginSuccessful() {
        LoginRequestDTO request = new LoginRequestDTO("manuelnovela48@gmail.com", "Novela123");
        User registedUser = new User(new RegisterRequestDTO("Manuel", "manuelnovela48@gmail.com", "Novela123"));
        String token = "eyJhbGciOiJIUzI1NiIsI5IkpXVCJ9.eyJpc3MiOiJhcGkuc2VyY3VyaXR5LnRAZ21haWwuY29tIn0.LyPrr9";

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(registedUser));
        when(passwordEncoder.matches(request.password(), registedUser.getPassword())).thenReturn(true);
        when(tokenService.generateToken(registedUser)).thenReturn(token);

        ResponseEntity<RestResponseDTO> response = authService.login(request);

        assertEquals(response.getStatusCodeValue(), 200);
        assertInstanceOf(LoginResponseDTO.class, response.getBody().getData());
        LoginResponseDTO responseData = (LoginResponseDTO) response.getBody().getData();

        assertEquals(responseData.name(), "Manuel");
        assertEquals(responseData.token(), token);

        verify(userRepository, times(1)).findByEmail(request.email());
        verify(passwordEncoder, times(1)).matches(request.password(), registedUser.getPassword());
        verify(tokenService, times(1)).generateToken(registedUser);
    }

    @Test
    @DisplayName("Should throw exception when password is incorrect")
    void loginInvalidPassword() {
        LoginRequestDTO request = new LoginRequestDTO("manuelnovela48@gmail.com", "wrongpassword");
        User user = new User(new RegisterRequestDTO("Manuel", "manuelnovela48@gmail.com", "Novela123"));

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.password(), user.getPassword())).thenReturn(false);

        InvalidCredential exception = assertThrows(InvalidCredential.class, () -> authService.login(request));
        assertEquals(exception.getMessage(), "Invalid username and/or password");

        verify(userRepository, times(1)).findByEmail(request.email());
        verify(passwordEncoder, times(1)).matches(request.password(), user.getPassword());
    }



    // test - Register


    @Test
    @DisplayName("Should register successfully with valid credentials")
    void registerSuccessful() {
        RegisterRequestDTO request = new RegisterRequestDTO("Manuel", "manuelnovela48@gmail.com", "Novela123");
        User newUser = new User();
        newUser.setName(request.username());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        String token = "generated-jwt-token";

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(tokenService.generateToken(newUser)).thenReturn(token);

        ResponseEntity<RestResponseDTO> response = authService.register(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getData()).isInstanceOf(LoginResponseDTO.class);
        LoginResponseDTO responseData = (LoginResponseDTO) response.getBody().getData();
        assertEquals(responseData.name(), "Manuel");
        assertEquals(responseData.token(), token);

        verify(userRepository, times(1)).findByEmail(request.email());
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenService, times(1)).generateToken(newUser);
    }

    @Test
    @DisplayName("Should throw exception when user already exists")
    void registerUserAlreadyExists() {
        RegisterRequestDTO request = new RegisterRequestDTO("Manuel", "manuelnovela48@gmail.com", "Novela123");
        User existingUser = new User();
        existingUser.setEmail(request.email());

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(existingUser));

        UserAlreadyExists exception = assertThrows(UserAlreadyExists.class, () -> authService.register(request));
        assertEquals(exception.getMessage(),"Email already registed!");

        verify(userRepository, times(1)).findByEmail(request.email());
        verify(userRepository, never()).save(any(User.class));
        verify(tokenService, never()).generateToken(any(User.class));
    }

}