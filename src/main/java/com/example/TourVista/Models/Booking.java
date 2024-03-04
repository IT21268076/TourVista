package com.example.TourVista.Models;

import com.example.TourVista.Utils.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Booking {

        @Id
        @GeneratedValue( strategy = GenerationType.IDENTITY )
        private Long bookingId;
        private double totalAmount;
        private BookingStatus status;
        private Date checkInDate;
        private Date checkOutDate;
        private Date dateOfBooking;

        @ManyToOne
        @JoinColumn(name="roomType_id")
        private RoomType roomType;

        @ManyToOne
        @JoinColumn(name="user_id")
        private User user;

        @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
        private Set<SaveDiscount> saveDiscounts;

        @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
        private Set<SaveSupplements> saveSupplements;

        @OneToOne(mappedBy = "booking")
        private SaveRoomType saveRoomType;

        @OneToOne(mappedBy = "booking")
        private Payment payment;

}
