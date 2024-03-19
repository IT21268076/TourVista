package com.example.TourVista.Services;

import com.example.TourVista.DTOs.*;
import com.example.TourVista.Models.*;
import com.example.TourVista.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;
    private DiscountRepository discountRepository;
//    @Autowired
//    private DiscountRepository discountRepository;
//
    @Autowired
    private SeasonRepository seasonRepository;
//
//    @Autowired
//    private SupplementsRepository supplementsRepository;
//
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private  RoomSeasonPriceRepository roomSeasonPriceRepository;

    public ContractService(ContractRepository contractRepository, DiscountRepository discountRepository, SeasonRepository seasonRepository, RoomSeasonPriceRepository roomSeasonPriceRepository, RoomTypeRepository roomTypeRepository) {
        this.contractRepository = contractRepository;
        this.discountRepository = discountRepository;
        this.seasonRepository = seasonRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomSeasonPriceRepository = roomSeasonPriceRepository;
    }

    public ContractService() {

    }

    public List<ContractDTO> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNewContract(ContractDTO contractDTO) {

        Contract contract = convertToEntity(contractDTO);

        // Create a new set of DiscountDTO objects
        Set<DiscountDTO> discountDTOSet = contractDTO.getDiscounts();
        // Create a discount object Set
        Set<Discount> discountSet = new HashSet<>();
        // Save Discounts
        for (DiscountDTO discountDTO : discountDTOSet) {
            Discount discount = new Discount();
            discount.setName(discountDTO.getName());
            discount.setAmount(discountDTO.getAmount());
            discount.setDescription(discountDTO.getDescription());
            discountSet.add(discount);

        }

        contract.setDiscounts(discountSet);


        Set<SeasonDTO> seasonDTOs = contractDTO.getSeasons();
        Set<Season> seasonSet = new HashSet<>();
        // Save Seasons
        for (SeasonDTO seasonDTO : seasonDTOs) {
            Season season = new Season();
            //seasonIdList.add(seasonDTO.getSeasonId());
            season.setSeasonName(seasonDTO.getSeasonName());
            season.setStartDate(seasonDTO.getStartDate());
            season.setEndDate(seasonDTO.getEndDate());
            season.setMarkUpPercentage(seasonDTO.getMarkUpPercentage());

            season = seasonRepository.save(season);
            seasonSet.add(season);

            List<RoomTypeDTO> roomTypes = seasonDTO.getRoomTypes();
            Set<RoomType> roomTypeSet = new HashSet<>();
            for (RoomTypeDTO roomTypeDTO : roomTypes) {
                RoomType roomType = new RoomType();
                roomType.setType(roomTypeDTO.getType());
                roomType.setAvailability(roomTypeDTO.getAvailability());
                roomType.setMaxNoOfGuests(roomTypeDTO.getMaxNoOfGuests());

                roomType = roomTypeRepository.save(roomType);
                roomTypeSet.add(roomType);

                Set<RoomSeasonPriceDTO> roomSeasonPriceDTOs = roomTypeDTO.getRoomSeasonPrices();

                for(RoomSeasonPriceDTO roomSeasonPriceDTO : roomSeasonPriceDTOs ) {
                    RoomSeasonPrice roomSeasonPrice = new RoomSeasonPrice();
                    roomSeasonPrice.setSeason(season);
                    roomSeasonPrice.setRoomType(roomType);

                    // Create a new instance of RoomSeasonPriceKey for each entity
                    RoomSeasonPriceKey roomSeasonPriceKey = new RoomSeasonPriceKey(roomType.getRoomTypeId(), season.getSeasonId());

                    roomSeasonPrice.setId(roomSeasonPriceKey);
                    roomSeasonPrice.setPrice(roomSeasonPriceDTO.getPrice());
                    roomSeasonPrice.setRoomCount(roomSeasonPriceDTO.getRoomCount());

                    roomSeasonPriceRepository.save(roomSeasonPrice);
                }
            }
            contract.setRoomTypes(roomTypeSet);
        }

        contract.setSeasons(seasonSet);

        // Save Supplements for the Season
        Set<SupplementsDTO> supplementsDTOs = new HashSet<>(contractDTO.getSupplements());
        Set<Supplements> supplementsSet = new HashSet<>();

        for (SupplementsDTO supplementsDTO : supplementsDTOs) {
            Supplements supplements = new Supplements();
            supplements.setName(supplementsDTO.getName());
            supplements.setPrice(supplementsDTO.getPrice());
            supplements.setDescription(supplementsDTO.getDescription());

            supplementsSet.add(supplements);
        }

        contract.setSupplements(supplementsSet);

        // Save Contract
        contractRepository.save(contract);
    }

    @Transactional
    public void updateContract(Long contractId, ContractDTO contractDTO) {
        Contract contractExists = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalStateException("Contract with id: " + contractId + " does not exist"));

        // Update contract entity with DTO values
        BeanUtils.copyProperties(contractDTO, contractExists, "contractId");

        contractRepository.save(contractExists);
    }

    public void deleteContract(Long contractId) {
        boolean exists = contractRepository.existsById(contractId);
        if (!exists) {
            throw new IllegalStateException("Contract with id: " + contractId + " does not exist");
        }
        contractRepository.deleteById(contractId);
    }

    // Helper method to convert Contract entity to ContractDTO
    private ContractDTO convertToDTO(Contract contract) {
        ContractDTO contractDTO = new ContractDTO();
        BeanUtils.copyProperties(contract, contractDTO);
        return contractDTO;
    }

    // Helper method to convert ContractDTO to Contract entity
    private Contract convertToEntity(ContractDTO contractDTO) {
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDTO, contract);
        return contract;
    }
}
