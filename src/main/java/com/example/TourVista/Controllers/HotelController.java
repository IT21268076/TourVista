package com.example.TourVista.Controllers;

import com.example.TourVista.DTOs.HotelDTO;
import com.example.TourVista.DTOs.HotelSearchDTO;
import com.example.TourVista.Models.Contract;
import com.example.TourVista.Models.Hotel;
import com.example.TourVista.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path ="api/hotel")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getHotels(){
        return hotelService.getHotels();
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel != null) {
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/search")
    public List<HotelSearchDTO> searchHotels(
            @RequestParam("checkInDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,

            @RequestParam("checkOutDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,

            @RequestParam String location
            ){ return hotelService.searchHotels(checkInDate, checkOutDate, location);}

    @PostMapping
    public void createHotel(@RequestBody HotelDTO hotelDTO){
        hotelService.addNewHotel(hotelDTO);
    }

    @PutMapping(path = "{hotelId}")
    public void updateHotel(
            @PathVariable("hotelId") Long hotelId,
            @RequestBody(required = false) String name,
            @RequestBody(required = false) String no,
            @RequestBody(required = false) String street,
            @RequestBody(required = false) String city,
            @RequestBody(required = false) String email,
            @RequestBody(required = false) String contactNo
    ){
        hotelService.updateHotel(hotelId, name, no, street, city, email, contactNo);
    }

    @DeleteMapping(path="{hotelId}")
    public void deleteHotel(
            @PathVariable("hotelId") Long hotelId
    ){
        hotelService.deleteHotel(hotelId);
    }
}
