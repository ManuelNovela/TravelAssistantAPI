package com.manuelnovela.TravelAssistant.repositories.interfaces;

import com.manuelnovela.TravelAssistant.domain.user.User;
import com.manuelnovela.TravelAssistant.dtos.RegisterRequestDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should get User sucessfully from DB")
    void findByEmailWhenExist() {
        String email = "manuelnovela48@gmail.com";
        RegisterRequestDTO data  = new RegisterRequestDTO("Manuel", "manuelnovela48@gmail.com", "Novela");
        this.createUser(data);
        Optional<User> result = this.userRepository.findByEmail(email);
        assertThat(result.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Should not get User sucessfully from DB when User not exists")
    void WhenExistWhenNotExist() {
        String email = "manuelnovela48@gmail.com";
        Optional<User> result = this.userRepository.findByEmail(email);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should not get User sucessfully when search email is null")
    void WhenExistWhenSerachNull() {
        String email = null;
        Optional<User> result = this.userRepository.findByEmail(email);
        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(RegisterRequestDTO data){
        User newuser = new User(data);
        this.entityManager.persist(newuser);
        return newuser;
    }
}