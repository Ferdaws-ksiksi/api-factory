package com.example.apifactory.dto;

import com.example.apifactory.domain.ClientType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/** Input DTO for creating/updating clients. */
@Data
public class ClientDTO {
    @NotNull private ClientType type;
    @NotBlank private String name;
    @Pattern(regexp="^\\+?[1-9]\\d{1,14}$", message="Phone must be E.164 format")
    private String phone;
    @Email private String email;
    private LocalDate birthDate;
    private String companyIdentifier;
}
