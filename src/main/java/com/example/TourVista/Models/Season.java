package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "seasonId")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonId;
    private String seasonName;
    private Date startDate;
    private Date endDate;
    private double markUpPercentage;

//    @ManyToOne
//    @JoinColumn(name = "contractId")
//    private Contract contract;

    @ManyToMany
    @JoinTable(
            name = "SupplementsForSeasons",
            joinColumns = @JoinColumn(name = "seasonId"),
            inverseJoinColumns = @JoinColumn(name = "supplementId"))
    Set<Supplements> supplementsSet;

//    @OneToMany(mappedBy = "season")
//    Set<RoomSeasonPrice> prices;

//    @OneToMany(mappedBy = "season")
//    Set<RoomSeasonPrice> roomCount;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<RoomSeasonPrice> roomSeasonPrices = new HashSet<>();

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<SupplementsSeasonPrice> supplementsSeasonPrices = new HashSet<>();

    // Define a method to check if the season is valid for the given dates
    public boolean isValidForDates(Date checkInDate, Date checkOutDate) {
        return (checkInDate.after(this.getStartDate()) || checkInDate.equals(this.getStartDate())) &&
                (checkOutDate.before(this.getEndDate()) || checkOutDate.equals(this.getEndDate()));
    }
}
