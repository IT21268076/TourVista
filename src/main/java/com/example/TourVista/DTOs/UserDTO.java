package com.example.TourVista.DTOs;

import com.example.TourVista.Utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobileNo;
    private UserRole role;
    private Set<BookingDTO> bookings;
}
