package com.example.apifactory.controller;

import com.example.apifactory.domain.*;
import com.example.apifactory.dto.ClientDTO;
import com.example.apifactory.dto.ClientResponseDTO;
import com.example.apifactory.dto.ContractResponseDTO;
import com.example.apifactory.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** REST endpoints for clients. */
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientDTO dto) {
        Client client = service.create(dto);
        ClientResponseDTO response = mapToResponseDTO(client);
        return ResponseEntity
                .created(URI.create("/api/v1/clients/" + client.getId()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> get(@PathVariable Long id) {
        Client client = service.get(id);
        ClientResponseDTO response = mapToResponseDTO(client);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ClientDTO dto) {
        Client client = service.update(id, dto);
        ClientResponseDTO response = mapToResponseDTO(client);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Helper method to map Client entity to ClientResponseDTO
    private ClientResponseDTO mapToResponseDTO(Client client) {
        List<ContractResponseDTO> contractDTOs = new ArrayList<>();

        // ✅ Null check to prevent NullPointerException
        if (client.getContracts() != null) {
            contractDTOs = client.getContracts().stream()
                    .map(this::mapContractToResponseDTO)
                    .collect(Collectors.toList());
        }

        return ClientResponseDTO.builder()
                .id(client.getId())
                .type(client.getType())
                .name(client.getName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .birthDate(client.getBirthDate())
                .companyIdentifier(client.getCompanyIdentifier())
                .deleted(client.getDeleted())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .contracts(contractDTOs)
                .build();
    }

    private ContractResponseDTO mapContractToResponseDTO(Contract contract) {
        return ContractResponseDTO.builder()
                .id(contract.getId())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .costAmount(contract.getCostAmount())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}