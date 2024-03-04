package com.example.TourVista.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Supplements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplementId;
    private String name;
    private double price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "contractId")
    private Contract contract;

    @ManyToMany(mappedBy = "supplementsSet")
    Set<Season> seasonSet;
}
