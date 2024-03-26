package com.example.TourVista.DTOs;

import com.example.TourVista.Models.Season;
import com.example.TourVista.Models.Supplements;
import com.example.TourVista.Models.SupplementsSeasonPriceKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplementsSeasonPriceDTO {
    private SupplementsSeasonPriceKey id;
    private Season season;
    private Supplements supplements;
    private double price;

}
