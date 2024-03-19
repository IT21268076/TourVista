package com.example.TourVista.Controllers;

import com.example.TourVista.DTOs.BookingDTO;
import com.example.TourVista.Models.Booking;
import com.example.TourVista.Services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/booking")
public class BookingController {

    private final BookingService bookingService;
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping
    public void addNewBooking(@RequestParam("roomTypeId") Long roomTypeId,
                              @RequestParam("seasonId") Long seasonId, @RequestBody BookingDTO bookingDTO){
        bookingService.addNewBooking(roomTypeId, seasonId, bookingDTO);
    }

    @PutMapping(path="{bookingId}")
    public void updateBooking(Long bookingId, Booking booking){
        bookingService.updateBooking(bookingId, booking);
    }
}
