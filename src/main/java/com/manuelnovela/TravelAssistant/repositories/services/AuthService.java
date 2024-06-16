package com.manuelnovela.TravelAssistant.repositories.services;

import com.manuelnovela.TravelAssistant.domain.user.User;
import com.manuelnovela.TravelAssistant.dtos.LoginRequestDTO;
import com.manuelnovela.TravelAssistant.dtos.LoginResponseDTO;
import com.manuelnovela.TravelAssistant.dtos.RegisterRequestDTO;
import com.manuelnovela.TravelAssistant.dtos.RestResponseDTO;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.InvalidCredential;
import com.manuelnovela.TravelAssistant.infrastructure.exceptions.UserAlreadyExists;
import com.manuelnovela.TravelAssistant.repositories.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ResponseEntity<RestResponseDTO> login (@RequestBody LoginRequestDTO body){

        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {

            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(createSucessResponse(new LoginResponseDTO(user.getName(), token)));

        }else{
            throw new InvalidCredential("Invalid username and/or password");
        }
    }


    public ResponseEntity<RestResponseDTO> register (@RequestBody RegisterRequestDTO body){

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
