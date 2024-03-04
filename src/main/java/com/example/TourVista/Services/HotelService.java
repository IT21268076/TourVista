package com.example.TourVista.Services;

import com.example.TourVista.Models.Hotel;
import com.example.TourVista.Repositories.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    public void addNewHotel(Hotel hotel) {
        Optional<Hotel> hotelOptional = hotelRepository.findHotelByEmail(hotel.getEmail());
        if(hotelOptional.isPresent()){
            throw new IllegalStateException("Email already exisits");
        }
        hotelRepository.save(hotel);
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
}
