package com.example.TourVista.Controllers;


import com.example.TourVista.DTOs.RoomTableDTO;
import com.example.TourVista.DTOs.RoomTypeDTO;
import com.example.TourVista.Models.RoomType;
import com.example.TourVista.Services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path ="api/roomType")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeController (
            RoomTypeService roomTypeService
    ){
        this.roomTypeService = roomTypeService;
    }

    @PostMapping
    public void createRoomType(@RequestBody RoomTypeDTO roomTypeDTO){
        roomTypeService.addNewRoomType(roomTypeDTO);
    }

    @GetMapping
    public Set<RoomTableDTO> loadRoomTypes(@RequestParam("hotelId") Long hotelId,
                                           @RequestParam("checkInDate")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,

                                           @RequestParam("checkOutDate")
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate){
        return roomTypeService.getRoomTypesByHotelId(hotelId, checkInDate, checkOutDate);
    }
}
