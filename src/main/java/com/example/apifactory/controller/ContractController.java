package com.example.apifactory.controller;

import com.example.apifactory.domain.Contract;
import com.example.apifactory.dto.ContractDTO;
import com.example.apifactory.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/** REST endpoints for contracts. */
@RestController
@RequestMapping("/api/v1/clients/{clientId}/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService service;

    @PostMapping
    public Contract create(@PathVariable UUID clientId,@Valid @RequestBody ContractDTO dto){
        return service.create(clientId, dto);
    }

    @PatchMapping("/{contractId}")
    public Contract updateCost(@PathVariable UUID clientId,@PathVariable UUID contractId,
                               @Valid @RequestBody ContractDTO dto){
        return service.updateCost(clientId, contractId, dto);
    }

    @GetMapping
    public Page<Contract> listActive(@PathVariable UUID clientId,
                                     @RequestParam(required=false) Instant updatedAfter,
                                     Pageable pageable){
        return service.listActive(clientId, updatedAfter, pageable);
    }

    @GetMapping("/sum-active")
    public Map<String,Object> sumActive(@PathVariable UUID clientId){
        BigDecimal sum = service.sumActive(clientId);
        return Map.of("clientId", clientId, "sumActiveContractCost", sum);
    }
}
