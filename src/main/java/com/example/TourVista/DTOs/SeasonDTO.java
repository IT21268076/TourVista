package com.example.TourVista.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeasonDTO {
    private Long seasonId;
    private String seasonName;
    private Date startDate;
    private Date endDate;
    private double markUpPercentage;
    private Long contractId;
    private Set<Long> supplementIds;
}
