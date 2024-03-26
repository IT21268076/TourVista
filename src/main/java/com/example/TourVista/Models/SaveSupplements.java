package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "supplementId")
public class SaveSupplements {
    @Id
    @GeneratedValue
    private long supplementId;
    private String name;
    private double price;

//    @ManyToOne
//    @JoinColumn(name="booking_id")
//    private Booking booking;

}
