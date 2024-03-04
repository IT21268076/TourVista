package com.example.TourVista.Models;

import com.example.TourVista.Utils.RoomAvailability;
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
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    private String type;
    private RoomAvailability availability;
    private int maxNoOfGuests;

    @ManyToOne
    @JoinColumn(name = "contractId")
    private Contract contract;

//    @OneToMany(mappedBy = "roomType")
//    Set<RoomSeasonPrice> prices;

//    @OneToMany(mappedBy = "roomType")
//    Set<RoomSeasonPrice> roomCount;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "roomType")
    Set<RoomSeasonPrice> roomSeasonPrices = new HashSet<>();


    public RoomType(String type) {
        this.type = type;
    }
}
