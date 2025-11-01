package com.example.apifactory.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractDTO {
    // If not provided, will be set to current date
    private LocalDate startDate;

    // Can be null
    private LocalDate endDate;

    @NotNull(message = "Cost amount is required")
    @DecimalMin(value = "0.01", message = "Cost amount must be greater than 0")
    private BigDecimal costAmount;
}