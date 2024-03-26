package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "supplementId")
public class Supplements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplementId;
    private String name;
    private double price;
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "contractId")
//    private Contract contract;

//    @ManyToMany(mappedBy = "supplementsSet")
//    Set<Season> seasonSet;

    @OneToMany(mappedBy = "supplements", cascade = CascadeType.ALL)
    Set<SupplementsSeasonPrice> supplementsSeasonPrices = new HashSet<>();
}
