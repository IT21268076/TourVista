package com.example.TourVista.Services;

import com.example.TourVista.DTOs.DiscountDTO;
import com.example.TourVista.DTOs.RoomSeasonPriceDTO;
import com.example.TourVista.DTOs.RoomTableDTO;
import com.example.TourVista.DTOs.RoomTypeDTO;
import com.example.TourVista.Models.*;
import com.example.TourVista.Repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomTypeService {

    @Autowired
    private final ContractRepository contractRepository;

    @Autowired
    private RoomSeasonPriceRepository roomSeasonPriceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SupplementsService supplementsService;

    @Autowired
    private DiscountRepository discountRepository;

    public RoomTypeService(ContractRepository contractRepository, DiscountRepository discountRepository, RoomSeasonPriceRepository roomSeasonPriceRepository, BookingRepository bookingRepository, SupplementsService supplementsService) {
        this.contractRepository = contractRepository;
        this.roomSeasonPriceRepository = roomSeasonPriceRepository;
        this.bookingRepository = bookingRepository;
        this.supplementsService = supplementsService;
        this.discountRepository = discountRepository;
    }
    public void addNewRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        BeanUtils.copyProperties(roomTypeDTO, roomType);
    }

    public Set<RoomTableDTO> getRoomTypesByHotelId(Long hotelId, Date checkInDate, Date checkOutDate) {
        // Step 1: Find Contracts by Hotel ID
        List<Contract> contracts = contractRepository.findByHotel_HotelId(hotelId);

        // Filter contracts based on the check-in and check-out dates
        contracts = contracts.stream()
                .filter(contract ->
                        // Check if the contract's season is valid for the given dates
                        (checkInDate.after(contract.getStartDate()) || checkInDate.equals(contract.getStartDate())) &&
                                (checkOutDate.before(contract.getEndDate()) || checkOutDate.equals(contract.getEndDate())))
                .toList();

        Set<RoomTableDTO> roomTableDTOSet = new HashSet<>();
        // Step 2: Retrieve RoomTypes with relevant Season Prices for each Contract
        Set<RoomTableDTO> availableRoomTypes = new HashSet<>();
        for (Contract contract : contracts) {
            Long contractId = contract.getContractId();
            Set<RoomType> roomTypesForContract = contract.getRoomTypes(); // Assuming a method to get room types from a contract
            for (RoomType roomType : roomTypesForContract) {
                RoomType roomTypeWithPrices = new RoomType();
                RoomTableDTO roomTableDTO = new RoomTableDTO();

                // Fetch RoomSeasonPrices based on RoomType
                List<RoomSeasonPrice> roomSeasonPrices = roomSeasonPriceRepository.findByRoomType(roomType);
                for (RoomSeasonPrice roomSeasonPrice : roomSeasonPrices) {
                    Season season = roomSeasonPrice.getSeason();
                    if (season != null && season.isValidForDates(checkInDate, checkOutDate)) {
                        roomTypeWithPrices.getRoomSeasonPrices().add(roomSeasonPrice); // Add RoomSeasonPrice to the copied RoomType
                        roomTableDTO.setSeasonId(season.getSeasonId());
                        roomTableDTO.setRoomTypeId(roomSeasonPrice.getRoomType().getRoomTypeId());
                        roomTableDTO.setType(roomSeasonPrice.getRoomType().getType());
                        roomTableDTO.setPrice(roomSeasonPrice.getPrice());
                        roomTableDTO.setSeasonName(roomSeasonPrice.getSeason().getSeasonName());

                        Long seasonid = season.getSeasonId();
                        roomTableDTO.setSupplementSet(supplementsService.getSupplementsBySeasonId(seasonid));

                        // Check if the room is available within the season's dates
                        if ((checkInDate.after(season.getStartDate()) || checkInDate.equals(season.getStartDate())) &&
                                (checkOutDate.before(season.getEndDate()) || checkOutDate.equals(season.getEndDate()))) {
                            // Add the roomTableDTO only if it's available in the season
                            availableRoomTypes.add(roomTableDTO);
                        }

                        roomTableDTOSet.add(roomTableDTO);
                    }
                }

                // Retrieve discounts by contractId
                Set<Discount> discounts = discountRepository.findDiscountsByContract_ContractId(contractId);

                Set<DiscountDTO> discountDTOSet = new HashSet<>();
                for (Discount discount : discounts) {
                    if ((checkInDate.after(discount.getStartDate()) || checkInDate.equals(discount.getStartDate())) &&
                            (checkOutDate.before(discount.getEndDate()) || checkOutDate.equals(discount.getEndDate())) ||
                            (checkInDate.after(discount.getStartDate()) && checkInDate.before(discount.getEndDate()))) {

                        DiscountDTO discountDTO = new DiscountDTO();
                        discountDTO.setDiscountId(discount.getDiscountId());
                        discountDTO.setAmount(discount.getAmount());
                        discountDTO.setName(discount.getName());
                        discountDTO.setDescription(discount.getDescription());
                        discountDTO.setStartDate(discount.getStartDate());
                        discountDTO.setEndDate(discount.getEndDate());

                        discountDTOSet.add(discountDTO);
                    }

                    roomTableDTO.setDiscounts(discountDTOSet);
//
                    // Check room availability
                    boolean isRoomAvailable = isRoomAvailable(roomType, checkInDate, checkOutDate);
                    if (isRoomAvailable) {
                        availableRoomTypes.addAll(roomTableDTOSet);
                    }
                }
            }
        }

        return availableRoomTypes;
    }

//    public Set<RoomTableDTO> getRoomTypesByHotelId(Long hotelId, Date checkInDate, Date checkOutDate) {
//        // Step 1: Find Contracts by Hotel ID
//        List<Contract> contracts = contractRepository.findByHotel_HotelId(hotelId);
//
//        // Filter contracts based on the check-in and check-out dates
//        Set<RoomTableDTO> availableRoomTypes = new HashSet<>();
//        for (Contract contract : contracts) {
//            Set<RoomType> roomTypesForContract = contract.getRoomTypes(); // Assuming a method to get room types from a contract
//            for (RoomType roomType : roomTypesForContract) {
//                // Fetch RoomSeasonPrices based on RoomType
//                List<RoomSeasonPrice> roomSeasonPrices = roomSeasonPriceRepository.findByRoomType(roomType);
//                for (RoomSeasonPrice roomSeasonPrice : roomSeasonPrices) {
//                    Season season = roomSeasonPrice.getSeason();
//                    if (season != null && season.isValidForDates(checkInDate, checkOutDate)) {
//                        RoomTableDTO roomTableDTO = new RoomTableDTO();
//                        roomTableDTO.setSeasonId(season.getSeasonId());
//                        roomTableDTO.setRoomTypeId(roomType.getRoomTypeId());
//                        roomTableDTO.setType(roomType.getType());
//                        roomTableDTO.setPrice(roomSeasonPrice.getPrice());
//                        roomTableDTO.setSeasonName(season.getSeasonName());
//                        roomTableDTO.setSupplementSet(supplementsService.getSupplementsBySeasonId(season.getSeasonId()));
//                        availableRoomTypes.add(roomTableDTO);
//                    }
//                }
//            }
//        }
//
//        return availableRoomTypes;
//    }

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
