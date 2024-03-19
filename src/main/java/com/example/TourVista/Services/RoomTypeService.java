package com.example.TourVista.Services;

import com.example.TourVista.DTOs.RoomSeasonPriceDTO;
import com.example.TourVista.DTOs.RoomTableDTO;
import com.example.TourVista.DTOs.RoomTypeDTO;
import com.example.TourVista.Models.*;
import com.example.TourVista.Repositories.BookingRepository;
import com.example.TourVista.Repositories.ContractRepository;
import com.example.TourVista.Repositories.RoomSeasonPriceRepository;
import com.example.TourVista.Repositories.SeasonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoomTypeService {

    @Autowired
    private final ContractRepository contractRepository;

    @Autowired
    private RoomSeasonPriceRepository roomSeasonPriceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public RoomTypeService(ContractRepository contractRepository, RoomSeasonPriceRepository roomSeasonPriceRepository, BookingRepository bookingRepository) {
        this.contractRepository = contractRepository;
        this.roomSeasonPriceRepository = roomSeasonPriceRepository;
        this.bookingRepository = bookingRepository;
    }
    public void addNewRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        BeanUtils.copyProperties(roomTypeDTO, roomType);
    }

    public Set<RoomTableDTO> getRoomTypesByHotelId(Long hotelId, Date checkInDate, Date checkOutDate) {
        // Step 1: Find Contracts by Hotel ID
        List<Contract> contracts = contractRepository.findByHotel_HotelId(hotelId);

        Set<RoomTableDTO> roomTableDTOSet = new HashSet<>();
        // Step 2: Retrieve RoomTypes with relevant Season Prices for each Contract
        Set<RoomTableDTO> availableRoomTypes = new HashSet<>();
        for (Contract contract : contracts) {
            Set<RoomType> roomTypesForContract = contract.getRoomTypes(); // Assuming a method to get room types from a contract
            for (RoomType roomType : roomTypesForContract) {
                RoomType roomTypeWithPrices = new RoomType(roomType);
                RoomTableDTO roomTableDTO = new RoomTableDTO();

                // Fetch RoomSeasonPrices based on RoomType
                List<RoomSeasonPrice> roomSeasonPrices = roomSeasonPriceRepository.findByRoomType(roomType);
                for (RoomSeasonPrice roomSeasonPrice : roomSeasonPrices) {
                    Season season = roomSeasonPrice.getSeason();
                    if (season != null) {
                        roomTypeWithPrices.getRoomSeasonPrices().add(roomSeasonPrice); // Add RoomSeasonPrice to the copied RoomType
                        roomTableDTO.setSeasonId(season.getSeasonId());
                        roomTableDTO.setRoomTypeId(roomSeasonPrice.getRoomType().getRoomTypeId());
                        roomTableDTO.setType(roomSeasonPrice.getRoomType().getType());
                        roomTableDTO.setPrice(roomSeasonPrice.getPrice());
                        roomTableDTO.setSeasonName(roomSeasonPrice.getSeason().getSeasonName());

                        roomTableDTO.setSupplementSet(season.getSupplementsSet());

                        roomTableDTOSet.add(roomTableDTO);
                    }
                }

                // Check room availability
                boolean isRoomAvailable = isRoomAvailable(roomType, checkInDate, checkOutDate);
                if (isRoomAvailable) {
                    availableRoomTypes.addAll(roomTableDTOSet);
                }
            }
        }

        return availableRoomTypes;
    }

    private boolean isRoomAvailable(RoomType roomType, Date checkInDate, Date checkOutDate) {
        // Fetch bookings for the room type within the date range
        List<Booking> bookings = bookingRepository.findByRoomType(roomType);

        // If there are no bookings, the room is available
        if (bookings.isEmpty()) {
            return true;
        }

        // Check if there are any overlapping bookings
        for (Booking booking : bookings) {
            Date bookingCheckInDate = booking.getCheckInDate();
            Date bookingCheckOutDate = booking.getCheckOutDate();

            // Check if the provided date range overlaps with any existing booking
            if ((checkInDate.after(bookingCheckInDate) && checkInDate.before(bookingCheckOutDate)) ||
                    (checkOutDate.after(bookingCheckInDate) && checkOutDate.before(bookingCheckOutDate)) ||
                    (checkInDate.before(bookingCheckInDate) && checkOutDate.after(bookingCheckOutDate))) {
                // Room is not available if there's any overlap
                return false;
            }
        }

        // Room is available if there are no overlapping bookings
        return true;
    }
}
