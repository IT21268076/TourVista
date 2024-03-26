package com.example.TourVista.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiscountDTO {
    private Long discountId;
    private String name;
    private double amount;
    private String description;
    private Date startDate;
    private Date endDate;

}
