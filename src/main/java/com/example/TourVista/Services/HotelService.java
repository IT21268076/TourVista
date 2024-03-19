package com.example.TourVista.Services;

import com.example.TourVista.DTOs.HotelDTO;
import com.example.TourVista.DTOs.HotelImageDTO;
import com.example.TourVista.DTOs.HotelSearchDTO;
import com.example.TourVista.Models.Contract;
import com.example.TourVista.Models.Hotel;
import com.example.TourVista.Models.HotelImages;
import com.example.TourVista.Repositories.ContractRepository;
import com.example.TourVista.Repositories.HotelImageRepository;
import com.example.TourVista.Repositories.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelService {

    @Autowired
    private final HotelRepository hotelRepository;

    @Autowired
    private final ContractRepository contractRepository;

    @Autowired
    private final HotelImageRepository hotelImageRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, ContractRepository contractRepository, HotelImageRepository hotelImageRepository) {
        this.hotelRepository = hotelRepository;
        this.contractRepository = contractRepository;
        this.hotelImageRepository = hotelImageRepository;
    }

    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }



    public void addNewHotel(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(hotelDTO, hotel);

        Optional<Hotel> hotelOptional = hotelRepository.findHotelByEmail(hotel.getEmail());
        if(hotelOptional.isPresent()){
            throw new IllegalStateException("Email already exisits");
        }

        hotelRepository.save(hotel);

        Set<HotelImageDTO> hotelImageDTOSet = hotelDTO.getHotelImages();
        Long hotelid = hotel.getHotelId();
        Set<HotelImages> hotelImageSet = new HashSet<>();
        for(HotelImageDTO hotelImageDTO : hotelImageDTOSet) {
            HotelImages hotelImage = new HotelImages();

            hotelImageDTO.setHotelId(hotelid);
            hotelImage.setImageUrl(hotelImageDTO.getImageUrl());
            hotelImage.setHotel(hotel);

            hotelImageSet.add(hotelImage);

            hotelImageRepository.save(hotelImage);
        }
        hotel.setHotelImages(hotelImageSet);


    }

    @Transactional
    public void updateHotel(Long hotelId, String name, String no, String street, String city, String email, String contactNo) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new IllegalStateException(("hotel with id: "+ hotelId + " does not exists")));

        if(name != null && !name.isEmpty() && !Objects.equals(hotel.getName(), name)){
            hotel.setName(name);
        }

        if((no != null && !no.isEmpty() && !Objects.equals(hotel.getNo(), no))
            && (street != null && !street.isEmpty() && !Objects.equals(hotel.getStreet(), street))
            && (city != null && !city.isEmpty() && !Objects.equals(hotel.getCity(), city))){
            hotel.setNo(no);
            hotel.setStreet(street);
            hotel.setCity(city);
        }

        if(email != null && !email.isEmpty() && !Objects.equals(hotel.getEmail(), email)){
            Optional<Hotel> hotelOptional = hotelRepository.findHotelByEmail(email);
            if(hotelOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            hotel.setName(email);
        }

        if(contactNo != null && !contactNo.isEmpty() && !Objects.equals(hotel.getContactNo(), contactNo)){
            hotel.setName(contactNo);
        }
    }

    public void deleteHotel(Long hotelId) {
        boolean exists = hotelRepository.existsById(hotelId);
        if(!exists){
            throw new IllegalStateException(("hotel with id : "+hotelId + "don't exists"));
        }
        hotelRepository.deleteById(hotelId);
    }

    public List<HotelSearchDTO> searchHotels(Date checkInDate, Date checkOutDate, String location) {

        // Step 2: Retrieve Contracts within the Date Range
        List<Contract> contracts = contractRepository.findByStartDateBeforeAndEndDateAfter(checkInDate, checkOutDate);

        Set<Long> hotelIds = new HashSet<>(); // To avoid duplicate hotels

        // Step 3 & 4: Retrieve Hotel Information and Eliminate Duplicate Hotels
        for (Contract contract : contracts) {
            hotelIds.add(contract.getHotel().getHotelId());
        }

        // Step 5: Filter Hotels by Location
        List<Hotel> hotelsInLocation = hotelRepository.findByCity(location);



        return getHotelSearchDTOS(hotelsInLocation, hotelIds);
    }

    // Step 6: Return the List of Hotels
    private static List<HotelSearchDTO> getHotelSearchDTOS(List<Hotel> hotelsInLocation, Set<Long> hotelIds) {
        List<HotelSearchDTO> searchResults = new ArrayList<>();
        for (Hotel hotel : hotelsInLocation) {
            if (hotelIds.contains(hotel.getHotelId())) {
                HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
                hotelSearchDTO.setName(hotel.getName());
                hotelSearchDTO.setHotelId(hotel.getHotelId());
                //hotelDTO.setImage(hotel.getImage());
                hotelSearchDTO.setLocation(hotel.getCity());
                searchResults.add(hotelSearchDTO);
            }
        }
        return searchResults;
    }

    public Hotel getHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId).orElse(null);
    }
}
