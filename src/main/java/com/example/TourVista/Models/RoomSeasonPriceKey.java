package com.example.TourVista.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RoomSeasonPriceKey implements Serializable {

    @Column(name = "season_id")
    Long seasonId;

    @Column(name = "roomtype_id")
    Long roomtypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomSeasonPriceKey that = (RoomSeasonPriceKey) o;
        return Objects.equals(seasonId, that.seasonId) && Objects.equals(roomtypeId, that.roomtypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seasonId, roomtypeId);
    }
}
