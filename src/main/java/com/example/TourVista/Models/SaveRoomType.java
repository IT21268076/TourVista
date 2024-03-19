package com.example.TourVista.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
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
