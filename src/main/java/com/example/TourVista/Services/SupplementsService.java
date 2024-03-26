package com.example.TourVista.Services;

import com.example.TourVista.Models.Supplements;
import com.example.TourVista.Models.SupplementsSeasonPrice;
import com.example.TourVista.Repositories.SupplementsSeasonPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SupplementsService {
    @Autowired
    private SupplementsSeasonPriceRepository supplementsSeasonPriceRepository;

    public Set<Supplements> getSupplementsBySeasonId(Long seasonId) {
        Set<SupplementsSeasonPrice> supplementsSeasonPrices = supplementsSeasonPriceRepository.findSupplementsSeasonPriceBySeason_SeasonId(seasonId);
        Set<Supplements> supplementsSet = new HashSet<>();
        for (SupplementsSeasonPrice supplementsSeasonPrice : supplementsSeasonPrices) {
            Supplements supplements = supplementsSeasonPrice.getSupplements();
            supplements.setPrice(supplementsSeasonPrice.getPrice());
            supplementsSet.add(supplements);
        }
        return supplementsSet;
    }

}
