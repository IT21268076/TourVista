package com.example.TourVista.Repositories;

import com.example.TourVista.Models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByStartDateBeforeAndEndDateAfter(Date startDate, Date endDate);

    List<Contract> findByHotel_HotelId(Long hotel_hotelId);

    List<Contract> findByRoomTypes_RoomTypeId(Long roomTypeId);

}
