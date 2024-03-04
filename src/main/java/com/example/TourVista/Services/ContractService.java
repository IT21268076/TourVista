package com.example.TourVista.Services;

import com.example.TourVista.DTOs.ContractDTO;
import com.example.TourVista.Models.Contract;
import com.example.TourVista.Repositories.ContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<ContractDTO> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void addNewContract(ContractDTO contractDTO) {
        Contract contract = convertToEntity(contractDTO);
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
