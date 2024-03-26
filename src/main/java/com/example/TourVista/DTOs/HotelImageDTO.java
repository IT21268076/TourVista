package com.example.TourVista.DTOs;

import com.example.TourVista.Models.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelImageDTO {

    private Long imageId;
    private String imageUrl;

    private Long hotelId;
}
