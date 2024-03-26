package com.example.TourVista.DTOs;

import com.example.TourVista.Models.*;
import com.example.TourVista.Utils.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingDTO {
    private Long bookingId;
    private double totalAmount;
    private BookingStatus status;
    private Date checkInDate;
    private Date checkOutDate;
    private Date dateOfBooking;
    private RoomType roomType;
    private Long userId;
    private String type;
    private double roomTypePrice;
    private Set<SaveDiscount> saveDiscounts;
    private Set<SaveSupplements> selectedSupplements;
    private SaveRoomType saveRoomType;
    private Payment payment;
}
