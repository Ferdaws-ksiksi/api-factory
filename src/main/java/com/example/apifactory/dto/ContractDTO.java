package com.example.apifactory.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractDTO {
    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull
    @DecimalMin(value="0.0", inclusive=false)
    private BigDecimal costAmount;
}