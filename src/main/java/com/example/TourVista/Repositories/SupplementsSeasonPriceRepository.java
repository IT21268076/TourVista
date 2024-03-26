package com.example.TourVista.Repositories;

import com.example.TourVista.Models.SupplementsSeasonPrice;
import com.example.TourVista.Models.SupplementsSeasonPriceKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SupplementsSeasonPriceRepository extends JpaRepository<SupplementsSeasonPrice, SupplementsSeasonPriceKey> {
    Set<SupplementsSeasonPrice> findSupplementsSeasonPriceBySeason_SeasonId(Long seasonId);
}
