package com.manuelnovela.TravelAssistant.controllers;

import com.manuelnovela.TravelAssistant.domain.user.User;
import com.manuelnovela.TravelAssistant.dtos.*;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.InvalidCredential;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.UserAlreadyExists;
import com.manuelnovela.TravelAssistant.infrastructure.security.TokenService;
import com.manuelnovela.TravelAssistant.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<RestResponseDTO>  login (@RequestBody LoginRequestDTO body){

        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {

            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(createSucessResponse(new LoginResponseDTO(user.getName(), token)));

        }else{
            throw new InvalidCredential("Invalid username and/or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponseDTO>  register (@RequestBody RegisterRequestDTO body){

        Optional<User> user = this.repository.findByEmail(body.email());
        if(user.isEmpty()){

            User newUser = new User();
            newUser.setName(body.username());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(createSucessResponse(new LoginResponseDTO(newUser.getName(), token)));

        }
        throw new UserAlreadyExists("Email already registed!");

    }
}
