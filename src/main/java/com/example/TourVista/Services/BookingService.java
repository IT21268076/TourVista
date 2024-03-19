package com.example.TourVista.Services;

import com.example.TourVista.DTOs.BookingDTO;
import com.example.TourVista.DTOs.DiscountDTO;
import com.example.TourVista.Models.*;
import com.example.TourVista.Repositories.BookingRepository;

import com.example.TourVista.Repositories.RoomSeasonPriceRepository;
import com.example.TourVista.Repositories.RoomTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomSeasonPriceService roomSeasonPriceService;
    @Autowired
    private RoomSeasonPriceRepository roomSeasonPriceRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    //public BookingService(){}

    public BookingService(BookingRepository bookingRepository, RoomSeasonPriceRepository roomSeasonPriceRepository, RoomSeasonPriceService roomSeasonPriceService, RoomTypeRepository roomTypeRepository) {
        this.bookingRepository = bookingRepository;
        this.roomSeasonPriceRepository = roomSeasonPriceRepository;
        this.roomSeasonPriceService = roomSeasonPriceService;
        this.roomTypeRepository = roomTypeRepository;
    }

    public BookingService() {

    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public void addNewBooking(Long roomTypeId, Long seasonId,BookingDTO bookingDTO) {
        Booking booking = new Booking();
        BeanUtils.copyProperties(bookingDTO, booking);

        // Fetch RoomType based on roomTypeId
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new IllegalArgumentException("RoomType not found for id: " + roomTypeId));

        booking.setRoomType(roomType);

        Set<SaveDiscount> saveDiscountSet = bookingDTO.getSaveDiscounts();
        // Create a save discount object Set
        // Save Discounts
        for (SaveDiscount saveDiscount : saveDiscountSet) {

            saveDiscount.setAmount(saveDiscount.getAmount());

            saveDiscountSet.add(saveDiscount);
        }

        booking.setSaveDiscounts(saveDiscountSet);

        Set<SaveSupplements> saveSupplementsSet = bookingDTO.getSaveSupplements();

        for (SaveSupplements saveSupplements : saveSupplementsSet) {

            saveSupplements.setName(saveSupplements.getName());
            saveSupplements.setPrice(saveSupplements.getPrice());

            saveSupplementsSet.add(saveSupplements);
        }

        booking.setSaveSupplements(saveSupplementsSet);

        SaveRoomType saveRoomType = new SaveRoomType();
        saveRoomType.setType(bookingDTO.getRoomType().getType());
        double price = roomSeasonPriceService.getPriceByRoomTypeAndSeason(roomTypeId,seasonId);
        saveRoomType.setPrice(price);

        booking.setSaveRoomType(saveRoomType);

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
