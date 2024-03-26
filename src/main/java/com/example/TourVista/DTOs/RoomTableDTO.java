package com.example.TourVista.DTOs;

import com.example.TourVista.Models.Supplements;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTableDTO {
    private Long roomTypeId;
    private Long seasonId;
    private String seasonName;
    private String type;
    private int maxNooOfGuests;
    private double price;
    private Set<Supplements> supplementSet;
    private Set<DiscountDTO> discounts;
}
