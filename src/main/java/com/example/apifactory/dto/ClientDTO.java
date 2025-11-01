package com.example.apifactory.dto;

import com.example.apifactory.domain.ClientType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/** Input DTO for creating/updating clients. */
@Data
public class ClientDTO {
    @NotNull(message = "Client type is required")
    private ClientType type;

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone must be in E.164 format")
    private String phone;

    @Email(message = "Email must be valid")
    private String email;

    // For PERSON type only
    private LocalDate birthDate;

    // For COMPANY type only - format: aaa-123
    @Pattern(regexp = "^[a-z]{3}-\\d{3}$", message = "Company identifier must be in format: aaa-123")
    private String companyIdentifier;
}