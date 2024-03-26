package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "contractId")
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
    private Set<Discount> discounts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
    private Set<Supplements> supplements;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
    private Set<Season> seasons;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
    private Set<RoomType> roomTypes;


}
