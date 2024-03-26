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
    @Autowired
    private SupplementsSeasonPriceRepository supplementsSeasonPriceRepository;

    @Autowired
    private SeasonRepository seasonRepository;
//
    @Autowired
    private SupplementsRepository supplementsRepository;
//
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private  RoomSeasonPriceRepository roomSeasonPriceRepository;

    public ContractService(ContractRepository contractRepository, DiscountRepository discountRepository, SeasonRepository seasonRepository, RoomSeasonPriceRepository roomSeasonPriceRepository, RoomTypeRepository roomTypeRepository, SupplementsRepository supplementsRepository) {
        this.contractRepository = contractRepository;
        this.discountRepository = discountRepository;
        this.seasonRepository = seasonRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomSeasonPriceRepository = roomSeasonPriceRepository;
        this.supplementsRepository = supplementsRepository;

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
            discount.setStartDate(discountDTO.getStartDate());
            discount.setEndDate(discountDTO.getEndDate());

            discountSet.add(discount);

        }

        contract.setDiscounts(discountSet);

        Set<SeasonDTO> seasonDTOs = contractDTO.getSeasons();
        Set<Season> seasonSet = new HashSet<>();
        Set<RoomType> roomTypeSet = new HashSet<>();
        Set<Supplements> supplementsSet = new HashSet<>();
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

            for (RoomTypeDTO roomTypeDTO : roomTypes) {
                RoomType roomType = new RoomType();

                roomType.setType(roomTypeDTO.getType());
                roomType.setAvailability(roomTypeDTO.getAvailability());
                roomType.setMaxNoOfGuests(roomTypeDTO.getMaxNoOfGuests());

                roomType = roomTypeRepository.save(roomType);
                roomTypeSet.add(roomType);

                Set<RoomSeasonPriceDTO> roomSeasonPriceDTOs = roomTypeDTO.getRoomSeasonPrices();

                for(RoomSeasonPriceDTO roomSeasonPriceDTO : roomSeasonPriceDTOs ) {
                    RoomSeasonPrice roomSeasonPrice = getRoomSeasonPrice(roomSeasonPriceDTO, season, roomType);

                    roomSeasonPriceRepository.save(roomSeasonPrice);
                }

            }

            contract.setRoomTypes(roomTypeSet);

            // Save Supplements for the Season
            Set<SupplementsDTO> supplementsDTOs = seasonDTO.getSupplements();

            for (SupplementsDTO supplementsDTO : supplementsDTOs) {
                Supplements supplement = new Supplements();

                supplement.setName(supplementsDTO.getName());
                supplement.setPrice(supplementsDTO.getPrice());
                supplement.setDescription(supplementsDTO.getDescription());

                supplement = supplementsRepository.save(supplement);
                supplementsSet.add(supplement);

                Set<SupplementsSeasonPriceDTO> supplementsSeasonPriceDTOs = supplementsDTO.getSupplementsSeasonPrices();

                for(SupplementsSeasonPriceDTO supplementsSeasonPriceDTO : supplementsSeasonPriceDTOs ) {
                    SupplementsSeasonPrice supplementsSeasonPrice = getSupplementSeasonPrice(supplementsSeasonPriceDTO, season, supplement);

                    supplementsSeasonPriceRepository.save(supplementsSeasonPrice);
                }
            }

            contract.setSupplements(supplementsSet);

        }

        contract.setSeasons(seasonSet);

        // Save Contract
        contractRepository.save(contract);
    }

    private static RoomSeasonPrice getRoomSeasonPrice(RoomSeasonPriceDTO roomSeasonPriceDTO, Season season, RoomType roomType) {
        RoomSeasonPrice roomSeasonPrice = new RoomSeasonPrice();
        roomSeasonPrice.setSeason(season);
        roomSeasonPrice.setRoomType(roomType);

        // Create a new instance of RoomSeasonPriceKey for each entity
        RoomSeasonPriceKey roomSeasonPriceKey = new RoomSeasonPriceKey(roomType.getRoomTypeId(), season.getSeasonId());

        roomSeasonPrice.setId(roomSeasonPriceKey);
        roomSeasonPrice.setPrice(roomSeasonPriceDTO.getPrice());
        roomSeasonPrice.setRoomCount(roomSeasonPriceDTO.getRoomCount());
        return roomSeasonPrice;
    }

    private static SupplementsSeasonPrice getSupplementSeasonPrice(SupplementsSeasonPriceDTO supplementsSeasonPriceDTO, Season season, Supplements supplement) {
        SupplementsSeasonPrice supplementsSeasonPrice = new SupplementsSeasonPrice();
        supplementsSeasonPrice.setSeason(season);
        supplementsSeasonPrice.setSupplements(supplement);

        // Create a new instance of RoomSeasonPriceKey for each entity
        SupplementsSeasonPriceKey supplementsSeasonPriceKey = new SupplementsSeasonPriceKey( season.getSeasonId(), supplement.getSupplementId());

        supplementsSeasonPrice.setId(supplementsSeasonPriceKey);
        supplementsSeasonPrice.setPrice(supplementsSeasonPriceDTO.getPrice());

        return supplementsSeasonPrice;
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
