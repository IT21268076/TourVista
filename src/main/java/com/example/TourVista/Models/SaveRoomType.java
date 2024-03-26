package com.example.TourVista.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "roomTypeId")
public class SaveRoomType {
    @Id
    @GeneratedValue
    private long roomTypeId;
    private String type;
    private double price;

    @OneToOne(mappedBy = "saveRoomType")
    private Booking booking;


    public SaveRoomType(SaveRoomType saveRoomTypeId) {
    }
}
