package com.example.apifactory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/** Output DTO for contract - updatedAt is NOT exposed per specs. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal costAmount;
    private Instant createdAt;
    // updatedAt is NOT included - per specs: "should not be exposed in the api"
}