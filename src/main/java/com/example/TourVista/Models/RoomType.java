package com.example.TourVista.Models;

import com.example.TourVista.Utils.RoomAvailability;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "roomTypeId")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    private String type;
    private RoomAvailability availability;
    private int maxNoOfGuests;

//    @ManyToOne
//    @JoinColumn(name = "contractId")
//    private Contract contract;

//    @OneToMany(mappedBy = "roomType")
//    Set<RoomSeasonPrice> prices;

//    @OneToMany(mappedBy = "roomType")
//    Set<RoomSeasonPrice> roomCount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bookingId")
    private Booking booking;


    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    Set<RoomSeasonPrice> roomSeasonPrices = new HashSet<>();

    public RoomType(String type) {
        this.type = type;
    }


}
