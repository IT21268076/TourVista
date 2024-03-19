package com.example.TourVista.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    private String name;
    private double amount;
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
//    private Contract contract;

}
