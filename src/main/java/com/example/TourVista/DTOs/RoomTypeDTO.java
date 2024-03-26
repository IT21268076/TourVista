package com.example.TourVista.DTOs;

import com.example.TourVista.Models.Booking;
import com.example.TourVista.Models.RoomSeasonPrice;
import com.example.TourVista.Utils.RoomAvailability;
import com.example.TourVista.Utils.UserRole;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTypeDTO {
    private Long roomTypeId;
    private String type;
    private RoomAvailability availability;
    private int maxNoOfGuests;
    private BookingDTO bookings;
    Set<RoomSeasonPriceDTO> roomSeasonPrices;


}
