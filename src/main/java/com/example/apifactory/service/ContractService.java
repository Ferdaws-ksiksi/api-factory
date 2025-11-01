package com.example.apifactory.service;

import com.example.apifactory.domain.*;
import com.example.apifactory.dto.ContractDTO;
import com.example.apifactory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/** Business logic for contracts. */
@Service
@RequiredArgsConstructor
public class ContractService {
    private final ClientRepository clientRepo;
    private final ContractRepository contractRepo;

    @Transactional
    public Contract create(Long clientId, ContractDTO dto) {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + clientId));

        Contract contract = new Contract();
        contract.setClient(client);
        // If startDate not provided, set to current date per specs
        contract.setStartDate(dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now());
        contract.setEndDate(dto.getEndDate()); // Can be null
        contract.setCostAmount(dto.getCostAmount());

        return contractRepo.save(contract);
    }

    @Transactional
    public Contract updateCost(Long clientId, Long contractId, ContractDTO dto) {
        Contract contract = contractRepo.findById(contractId)
                .orElseThrow(() -> new NotFoundException("Contract not found with id: " + contractId));

        // Verify contract belongs to client
        if (!contract.getClient().getId().equals(clientId)) {
            throw new IllegalArgumentException("Contract does not belong to the specified client");
        }

        // Update cost amount - updatedAt will be auto-updated by @PreUpdate
        contract.setCostAmount(dto.getCostAmount());

        return contractRepo.save(contract);
    }

    public Page<Contract> listActive(Long clientId, Instant updatedAfter, Pageable pageable) {
        // Verify client exists
        clientRepo.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + clientId));

        return contractRepo.findActiveByClientAndUpdatedAfter(
                clientId,
                LocalDate.now(),
                updatedAfter,
                pageable
        );
    }

    public BigDecimal sumActive(Long clientId) {
        // Verify client exists
        clientRepo.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + clientId));

        BigDecimal sum = contractRepo.sumActiveContracts(clientId, LocalDate.now());
        return sum != null ? sum : BigDecimal.ZERO;
    }
}