package com.example.TourVista.Services;

import com.example.TourVista.Models.RoomSeasonPriceKey;
import com.example.TourVista.Models.RoomSeasonPrice;
import com.example.TourVista.Repositories.RoomSeasonPriceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomSeasonPriceService {
    @Autowired
    private RoomSeasonPriceRepository roomSeasonPriceRepository;

    public RoomSeasonPriceService(RoomSeasonPriceRepository roomSeasonPriceRepository){
        this.roomSeasonPriceRepository = roomSeasonPriceRepository;
    }

    public double getPriceByRoomTypeAndSeason(Long roomTypeId, Long seasonId) {
        RoomSeasonPriceKey id = new RoomSeasonPriceKey(roomTypeId, seasonId);
        RoomSeasonPrice roomSeasonPrice = roomSeasonPriceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RoomSeasonPrice not found"));
        return roomSeasonPrice.getPrice();
    }
}
