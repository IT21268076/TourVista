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
public class SaveDiscount {
    @Id
    @GeneratedValue
    private long discountId;
    private double amount;

    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;
}

