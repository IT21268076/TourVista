package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class SupplementsSeasonPrice {

    @EmbeddedId
    private SupplementsSeasonPriceKey id = new SupplementsSeasonPriceKey();

    @ManyToOne
    @MapsId("seasonId")
    @JoinColumn(name = "season_id")
    @JsonIgnore
    private Season season;

    @ManyToOne
    @MapsId("supplementsId")
    @JoinColumn(name = "supplements_id")
    private Supplements supplements;

    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplementsSeasonPrice supplementsSeasonPrice = (SupplementsSeasonPrice) o;
        return Objects.equals(id, supplementsSeasonPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
