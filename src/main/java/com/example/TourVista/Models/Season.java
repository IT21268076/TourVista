package com.example.TourVista.Models;

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
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonId;
    private String seasonName;
    private Date startDate;
    private Date endDate;
    private double markUpPercentage;

    @ManyToOne
    @JoinColumn(name = "contractId")
    private Contract contract;

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

    @OneToMany(mappedBy = "season")
    Set<RoomSeasonPrice> roomSeasonPrices = new HashSet<>();
}
