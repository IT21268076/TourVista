package com.example.TourVista.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplementsDTO {
    private Long supplementId;
    private String name;
    private double price;
    private String description;
//    private Long contractId;
    private Set<SeasonDTO> seasons;
    private Set<SupplementsSeasonPriceDTO> supplementsSeasonPrices;
}
