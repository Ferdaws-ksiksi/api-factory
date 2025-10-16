package com.example.apifactory.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

/** Input DTO for creating/updating contracts. */
@Data
public class ContractDTO {
    private Instant startDate;
    private Instant endDate;
    @NotNull @DecimalMin(value="0.0", inclusive=false)
    private BigDecimal costAmount;
}
