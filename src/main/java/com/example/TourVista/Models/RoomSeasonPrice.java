package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class RoomSeasonPrice {

    @EmbeddedId
    private RoomSeasonPriceKey id = new RoomSeasonPriceKey();

    @ManyToOne
    @MapsId("seasonId")
    @JoinColumn(name = "season_id")
    @JsonIgnore
    private Season season;

    @ManyToOne
    @MapsId("roomtypeId")
    @JoinColumn(name = "roomtype_id")
    private RoomType roomType;

    private double price;
    private int roomCount;
}
