package com.example.TourVista.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Hotel {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long hotelId;
    private String name;
    private String no;
    private String street;
    private String city;
    private String email;
    private String contactNo;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private Set<Contract> contracts;
}
