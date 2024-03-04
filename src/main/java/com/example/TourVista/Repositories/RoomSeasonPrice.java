package com.example.TourVista.Repositories;

import com.example.TourVista.Models.RoomSeasonPriceKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomSeasonPrice extends JpaRepository<RoomSeasonPrice, RoomSeasonPriceKey> {
}
