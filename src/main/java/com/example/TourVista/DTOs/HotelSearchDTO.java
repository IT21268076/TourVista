package com.example.TourVista.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelSearchDTO {
    private Long hotelId;
    private String name;
    private String location;
    private String imageUrl;
}
