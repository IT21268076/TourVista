package com.example.TourVista.Repositories;

import com.example.TourVista.Models.RoomSeasonPrice;
import com.example.TourVista.Models.RoomSeasonPriceKey;
import com.example.TourVista.Models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomSeasonPriceRepository extends JpaRepository<RoomSeasonPrice, RoomSeasonPriceKey> {

    List<RoomSeasonPrice> findByRoomType(RoomType roomType);
}
