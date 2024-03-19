package com.example.TourVista.Controllers;

import com.example.TourVista.DTOs.ContractDTO;
import com.example.TourVista.Models.Contract;
import com.example.TourVista.Services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/contract")
public class ContractController {

    @Autowired
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public List<ContractDTO> getAllContracts() {
        return contractService.getAllContracts();
    }

    @PostMapping
    public void addNewContract(@RequestBody ContractDTO contractDTO) {
        contractService.addNewContract(contractDTO);
    }

    @PutMapping(path = "{contractId}")
    public void updateContract(
            @PathVariable("contractId") Long contractId,
            @RequestBody ContractDTO contractDTO
    ) {
        contractService.updateContract(contractId, contractDTO);
    }

    @DeleteMapping(path = "{contractId}")
    public void deleteContract(
            @PathVariable("contractId") Long contractId
    ) {
        contractService.deleteContract(contractId);
    }
}
