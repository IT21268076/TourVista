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

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name="roomType_id", referencedColumnName = "roomTypeId")
        private RoomType roomType;

        @ManyToOne
        @JoinColumn(name="user_id")
        private User user;

        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "booking_id", referencedColumnName = "bookingId")
        private Set<SaveDiscount> saveDiscounts;

        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "booking_id", referencedColumnName = "bookingId")
        private Set<SaveSupplements> saveSupplements;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "saveroomtype_id")
        private SaveRoomType saveRoomType;

//        @OneToOne(mappedBy = "booking")
//        private Payment payment;

}
