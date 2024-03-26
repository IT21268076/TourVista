package com.example.TourVista.DTOs;

import com.example.TourVista.Models.Discount;
import com.example.TourVista.Models.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractDTO {
    private Long contractId;
    private Date startDate;
    private Date endDate;
    private double prepaymentPercentage;
    private double cancellationFee;
    private int noOfBalancePaymentDates;
    private int noOfDatesOfCancellation;
    private Hotel hotel;
    private Set<DiscountDTO> discounts;
    private Set<SupplementsDTO> supplements;
    private Set<SeasonDTO> seasons;
    private Set<RoomTypeDTO> roomTypes;
}
