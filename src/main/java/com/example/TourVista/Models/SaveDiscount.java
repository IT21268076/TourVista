package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "discountId")
public class SaveDiscount {
    @Id
    @GeneratedValue
    private long discountId;
    private double amount;
    private Date startDate;
    private Date endDate;

//    @ManyToOne
//    @JoinColumn(name="booking_id", referencedColumnName = "bookingId")
//    private Booking booking;
}

