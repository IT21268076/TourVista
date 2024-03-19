package com.example.TourVista.Repositories;

import com.example.TourVista.Models.Booking;
import com.example.TourVista.Models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomType(RoomType roomType);
}
