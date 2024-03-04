package com.example.TourVista.Controllers;

import com.example.TourVista.Models.Hotel;
import com.example.TourVista.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public void createHotel(@RequestBody Hotel hotel){
        hotelService.addNewHotel(hotel);
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
