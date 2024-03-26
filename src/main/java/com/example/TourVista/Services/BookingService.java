package com.example.TourVista.Services;

import com.example.TourVista.DTOs.BookingDTO;
import com.example.TourVista.DTOs.DiscountDTO;
import com.example.TourVista.Models.*;
import com.example.TourVista.Repositories.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ContractRepository contractRepository;

    //public BookingService(){}

    public BookingService(DiscountRepository discountRepository, ContractRepository contractRepository, BookingRepository bookingRepository, RoomSeasonPriceRepository roomSeasonPriceRepository, RoomSeasonPriceService roomSeasonPriceService, RoomTypeRepository roomTypeRepository) {
        this.bookingRepository = bookingRepository;
        this.roomSeasonPriceRepository = roomSeasonPriceRepository;
        this.roomSeasonPriceService = roomSeasonPriceService;
        this.roomTypeRepository = roomTypeRepository;
        this.discountRepository = discountRepository;
        this.contractRepository = contractRepository;
    }

    public BookingService() {

    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

//    @Transactional
//    public void addNewBooking(Long roomTypeId, Long seasonId,BookingDTO bookingDTO) {
//        Booking booking = new Booking();
//        BeanUtils.copyProperties(bookingDTO, booking);
//
//        // Fetch RoomType based on roomTypeId
//        RoomType roomType = roomTypeRepository.findById(roomTypeId)
//                .orElseThrow(() -> new IllegalArgumentException("RoomType not found for id: " + roomTypeId));
//
//        booking.setRoomType(roomType);
//
//        // Retrieve the Contract ID associated with the RoomType
//        Long contractId = getContractIdByRoomTypeId(roomTypeId);
//
//        // Retrieve discounts by contractId
//        Set<Discount> discounts = discountRepository.findDiscountsByContract_ContractId(contractId);
//        Set<SaveDiscount> saveDiscountSet = new HashSet<>();
//        // Create a save discount object Set
//        // Save Discounts
//        for (Discount discount : discounts) {
//            SaveDiscount saveDiscount = new SaveDiscount();
//            saveDiscount.setAmount(discount.getAmount());
//            saveDiscountSet.add(saveDiscount);
//        }
//
//        booking.setSaveDiscounts(saveDiscountSet);
//
//        //Set<SaveSupplements> saveSupplementsSet = bookingDTO.getSaveSupplements();
//        Set<SaveSupplements> saveSupplementsSet = new HashSet<>();
//        // Add supplements from bookingDTO to saveSupplementsSet
//        for (SaveSupplements saveSupplements : bookingDTO.getSaveSupplements()) {
//            SaveSupplements supplement = new SaveSupplements();
//            supplement.setName(saveSupplements.getName());
//            supplement.setPrice(saveSupplements.getPrice());
//            saveSupplementsSet.add(supplement);
//        }
////        for (SaveSupplements saveSupplements : saveSupplementsSet) {
////
////            saveSupplements.setName(saveSupplements.getName());
////            saveSupplements.setPrice(saveSupplements.getPrice());
////
////            saveSupplementsSet.add(saveSupplements);
////        }
//
//        booking.setSaveSupplements(saveSupplementsSet);
//
//        SaveRoomType saveRoomType = new SaveRoomType();
//        saveRoomType.setType(bookingDTO.getRoomType().getType());
//        double price = roomSeasonPriceService.getPriceByRoomTypeAndSeason(roomTypeId,seasonId);
//        saveRoomType.setPrice(price);
//
//        booking.setSaveRoomType(saveRoomType);
//
//        bookingRepository.save(booking);
//    }

    @Transactional
    public void addNewBooking(Long roomTypeId, Long seasonId, BookingDTO bookingDTO) {
        Booking booking = new Booking();
        BeanUtils.copyProperties(bookingDTO, booking);

        // Fetch RoomType based on roomTypeId
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new IllegalArgumentException("RoomType not found for id: " + roomTypeId));

        booking.setRoomType(roomType);

        // Retrieve the Contract ID associated with the RoomType
        Long contractId = getContractIdByRoomTypeId(roomTypeId);

        // Retrieve discounts by contractId

        Set<SaveDiscount> saveDiscountSet = bookingDTO.getSaveDiscounts().stream()
                .map(discount -> {
                    SaveDiscount saveDiscount = new SaveDiscount();
                    saveDiscount.setAmount(discount.getAmount());
                    saveDiscount.setStartDate(discount.getStartDate());
                    saveDiscount.setEndDate(discount.getEndDate());
                    return saveDiscount;
                })
                .collect(Collectors.toSet());

        booking.setSaveDiscounts(saveDiscountSet);

         // Initialize saveSupplementsSet properly
        Set<SaveSupplements> saveSupplementsSet = bookingDTO.getSelectedSupplements().stream()
                .map(saveSupplements -> {
                    SaveSupplements supplement = new SaveSupplements();
                    supplement.setName(saveSupplements.getName());
                    supplement.setPrice(saveSupplements.getPrice());
                    return supplement;
                })
                .collect(Collectors.toSet());

        booking.setSaveSupplements(saveSupplementsSet);

        SaveRoomType saveRoomType = new SaveRoomType();
        saveRoomType.setType(bookingDTO.getType());
        saveRoomType.setPrice(bookingDTO.getRoomTypePrice());

        booking.setSaveRoomType(saveRoomType);

        booking.setDateOfBooking(LocalDate.now());

        bookingRepository.save(booking);
    }

//    public Long getContractIdByRoomTypeId(Long roomTypeId) {
//        RoomType roomType = roomTypeRepository.findById(roomTypeId)
//                .orElseThrow(() -> new IllegalArgumentException("RoomType not found for id: " + roomTypeId));
//
//        // Access the associated Contract and retrieve its contractId
//        Contract contract = roomType.getContractId();
//        if (contract != null) {
//            return contract.getContractId();
//        } else {
//            // Handle the case where no contract is associated with the RoomType
//            return null; // Or throw an exception, depending on your requirements
//        }
//
//    }
    public Long getContractIdByRoomTypeId(Long roomTypeId) {
        // Retrieve contracts associated with the given roomTypeId
        List<Contract> contracts = contractRepository.findByRoomTypes_RoomTypeId(roomTypeId);

        // If contracts exist, return the ID of the first contract
        if (!contracts.isEmpty()) {
            return contracts.get(0).getContractId();
        } else {
            // Handle the case where no contracts are associated with the RoomType
            throw new IllegalArgumentException("No contract found for RoomType ID: " + roomTypeId);
        }
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
