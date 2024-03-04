package com.example.TourVista.DTOs;

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
    private Long roomTypeId;
    private Long userId;
    private Set<Long> saveDiscountIds;
    private Set<Long> saveSupplementIds;
    private Long saveRoomTypeId;
    private Long paymentId;
}
