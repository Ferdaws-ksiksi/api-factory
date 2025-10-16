package com.example.apifactory.service;

import com.example.apifactory.domain.Client;
import com.example.apifactory.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

/** Business logic for managing clients. */
@Service @RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repo;

    public Client create(Client c){ return repo.save(c); }

    public Client get(UUID id){
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
    }

    public Client update(UUID id, Client changes){
        Client existing = get(id);
        existing.setName(changes.getName());
        existing.setPhone(changes.getPhone());
        existing.setEmail(changes.getEmail());
        return repo.save(existing);
    }

    @Transactional
    public void delete(UUID id){
        Client client = get(id);
        Instant now = Instant.now();
        client.getContracts().stream()
                .filter(ct -> ct.getEndDate()==null || ct.getEndDate().isAfter(now))
                .forEach(ct -> ct.setEndDate(now));
        client.setDeleted(true);
        repo.save(client);
    }
}
