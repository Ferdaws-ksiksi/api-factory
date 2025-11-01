package com.example.apifactory.controller;

import com.example.apifactory.domain.Contract;
import com.example.apifactory.dto.ContractDTO;
import com.example.apifactory.dto.ContractResponseDTO;
import com.example.apifactory.dto.TotalCostResponseDTO;
import com.example.apifactory.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/** REST endpoints for contracts. */
@RestController
@RequestMapping("/api/v1/clients/{clientId}/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService service;

    /**
     * Create a contract for a client.
     * - startDate defaults to current date if not provided
     * - endDate can be null
     * - updatedAt is managed internally and NOT exposed
     */
    @PostMapping
    public ResponseEntity<ContractResponseDTO> create(
            @PathVariable Long clientId,
            @Valid @RequestBody ContractDTO dto) {
        Contract contract = service.create(clientId, dto);
        ContractResponseDTO response = mapToResponseDTO(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Update the cost amount of a contract.
     * Automatically updates the internal updatedAt timestamp.
     */
    @PatchMapping("/{contractId}")
    public ResponseEntity<ContractResponseDTO> updateCost(
            @PathVariable Long clientId,
            @PathVariable Long contractId,
            @Valid @RequestBody ContractDTO dto) {
        Contract contract = service.updateCost(clientId, contractId, dto);
        ContractResponseDTO response = mapToResponseDTO(contract);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active contracts for a client.
     * - Returns only contracts where current_date < end_date
     * - Optional filter by updatedAt date
     */
    @GetMapping
    public ResponseEntity<Page<ContractResponseDTO>> listActive(
            @PathVariable Long clientId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant updatedAfter,
            Pageable pageable) {
        Page<Contract> contracts = service.listActive(clientId, updatedAfter, pageable);
        Page<ContractResponseDTO> response = contracts.map(this::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * PERFORMANT endpoint: Returns the sum of all active contract costs for a client.
     * Uses direct database SUM query for optimal performance.
     */
    @GetMapping("/total-cost")
    public ResponseEntity<TotalCostResponseDTO> sumActive(@PathVariable Long clientId) {
        BigDecimal sum = service.sumActive(clientId);
        TotalCostResponseDTO response = new TotalCostResponseDTO(clientId, sum);
        return ResponseEntity.ok(response);
    }

    // Helper method to map Contract entity to ContractResponseDTO
    private ContractResponseDTO mapToResponseDTO(Contract contract) {
        return ContractResponseDTO.builder()
                .id(contract.getId())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .costAmount(contract.getCostAmount())
                .createdAt(contract.getCreatedAt())
                // updatedAt NOT included per specs: "should not be exposed in the api"
                .build();
    }
}