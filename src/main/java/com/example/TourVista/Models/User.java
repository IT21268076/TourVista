package com.example.TourVista.Models;

import com.example.TourVista.Utils.UserRole;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobileNo;
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Booking> bookings;

}
