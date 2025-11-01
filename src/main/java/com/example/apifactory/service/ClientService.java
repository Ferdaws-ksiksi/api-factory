package com.example.apifactory.service;

import com.example.apifactory.domain.Client;
import com.example.apifactory.domain.ClientType;
import com.example.apifactory.dto.ClientDTO;
import com.example.apifactory.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

/** Business logic for managing clients. */
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repo;

    @Transactional
    public Client create(ClientDTO dto) {
        // Validation: PERSON must have birthDate
        if (dto.getType() == ClientType.PERSON && dto.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date is required for PERSON type");
        }

        // Validation: COMPANY must have companyIdentifier
        if (dto.getType() == ClientType.COMPANY &&
                (dto.getCompanyIdentifier() == null || dto.getCompanyIdentifier().isBlank())) {
            throw new IllegalArgumentException("Company identifier is required for COMPANY type");
        }

        Client client = Client.builder()
                .type(dto.getType())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .companyIdentifier(dto.getCompanyIdentifier())
                .deleted(false)
                .build();

        return repo.save(client);
    }

    public Client get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + id));
    }

    @Transactional
    public Client update(Long id, ClientDTO dto) {
        Client existing = get(id);

        // Update only allowed fields (NOT birthDate and NOT companyIdentifier per specs)
        existing.setName(dto.getName());
        existing.setPhone(dto.getPhone());
        existing.setEmail(dto.getEmail());
        // existing.setBirthDate() - FORBIDDEN per specs
        // existing.setCompanyIdentifier() - FORBIDDEN per specs

        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Client client = get(id);
        LocalDate now = LocalDate.now();

        // Update end date of all active contracts to current date
        client.getContracts().stream()
                .filter(contract -> contract.getEndDate() == null || contract.getEndDate().isAfter(now))
                .forEach(contract -> contract.setEndDate(now));

        // Soft delete the client
        client.setDeleted(true);
        repo.save(client);
    }
}