package com.example.TourVista.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;
    private Date startDate;
    private Date endDate;
    private double prepaymentPercentage;
    private double cancellationFee;
    private int noOfBalancePaymentDates;
    private int noOfDatesOfCancellation;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    @JoinColumn(name = "contractId")
    private Set<Discount> discounts;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<Supplements> supplements;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<Season> seasons;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<RoomType> roomTypes;


}
