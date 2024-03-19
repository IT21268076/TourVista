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
