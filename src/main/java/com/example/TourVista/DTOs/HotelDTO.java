package com.example.TourVista.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDTO {
    private Long hotelId;
    private String name;
    private String no;
    private String street;
    private String city;
    private String email;
    private String contactNo;
    private Set<ContractDTO> contracts;
    private Set<HotelImageDTO> hotelImages;

}
