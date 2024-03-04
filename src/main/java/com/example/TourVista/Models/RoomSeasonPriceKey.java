package com.example.TourVista.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RoomSeasonPriceKey implements Serializable {

    @Column(name = "season_id")
    Long seasonId;

    @Column(name = "roomtype_id")
    Long roomtypeId;
}
