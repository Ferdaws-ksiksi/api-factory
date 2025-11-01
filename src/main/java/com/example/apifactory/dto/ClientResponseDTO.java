package com.example.apifactory.dto;

import com.example.apifactory.domain.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/** Output DTO for returning client data with all fields. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private ClientType type;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String companyIdentifier;
    private Boolean deleted;
    private Instant createdAt;
    private Instant updatedAt;
    private List<ContractResponseDTO> contracts;
}