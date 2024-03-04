package com.example.TourVista.Services;

import com.example.TourVista.Models.Booking;
import com.example.TourVista.Models.Contract;
import com.example.TourVista.Repositories.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void addNewBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    @Transactional
    public void updateBooking(Long bookingId, Booking booking) {
        Booking bookingExists = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalStateException(("Booking with id: "+ bookingId + " does not exists")));
        bookingExists.setTotalAmount(booking.getTotalAmount());
        bookingExists.setCheckInDate(booking.getCheckInDate());
        bookingExists.setCheckOutDate(booking.getCheckOutDate());
        bookingExists.setStatus(booking.getStatus());

        bookingRepository.save(bookingExists);
    }

}
