package com.example.TourVista.DTOs;

import com.example.TourVista.Models.RoomSeasonPriceKey;
import com.example.TourVista.Models.RoomType;
import com.example.TourVista.Models.Season;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomSeasonPriceDTO {
    private RoomSeasonPriceKey id;
    private Season season;
    private RoomType roomType;
    private double price;
    private int roomCount;
}
