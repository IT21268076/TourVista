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
public class SupplementsSeasonPriceKey implements Serializable {

    @Column(name = "season_id")
    Long seasonId;

    @Column(name = "roomtype_id")
    Long supplementsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplementsSeasonPriceKey that = (SupplementsSeasonPriceKey) o;
        return Objects.equals(seasonId, that.seasonId) && Objects.equals(supplementsId, that.supplementsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seasonId, supplementsId);
    }
}
