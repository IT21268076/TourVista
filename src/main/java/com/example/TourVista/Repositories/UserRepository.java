package com.example.TourVista.Repositories;

import com.example.TourVista.Models.Hotel;
import com.example.TourVista.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String updatedEmail);
}
