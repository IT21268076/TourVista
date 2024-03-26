package com.example.TourVista.Repositories;

import com.example.TourVista.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findHotelByEmail(String email);

    List<Hotel> findByCity(String city);
}
