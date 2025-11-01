package com.example.apifactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalCostResponseDTO {
    private Long clientId;
    private BigDecimal totalActiveCost;
}