package com.manuelnovela.TravelAssistant.repositories.interfaces;

import com.manuelnovela.TravelAssistant.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
