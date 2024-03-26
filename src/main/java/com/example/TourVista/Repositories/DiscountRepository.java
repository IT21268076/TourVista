package com.example.TourVista.Repositories;

import com.example.TourVista.Models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Set<Discount> findDiscountsByContract_ContractId(Long contractId);
    //Set<Discount> findDiscountsByContract_ContractId(Long contractId);
}
