package com.example.TourVista.DTOs;

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
    private Long hotelId;
    private Set<Long> discountIds;
    private Set<Long> supplementIds;
    private Set<Long> seasonIds;
    private Set<Long> roomTypeIds;
}
