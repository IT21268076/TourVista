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
        property = "paymentId")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private boolean payStatus;
    private Date paidDate;
    private double paidAmount;
    private double prepaymentAmount;
    private double cancellationFee;
    private double markupPercentage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "bookingId")
    private Booking booking;
}
