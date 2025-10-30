package com.example.apifactory.controller;

import com.example.apifactory.domain.*;
import com.example.apifactory.dto.ClientDTO;
import com.example.apifactory.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

/** REST endpoints for clients. */
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody ClientDTO dto){
        if(dto.getType() == ClientType.PERSON && dto.getBirthDate() == null)
            return ResponseEntity.badRequest().build();
        if(dto.getType() == ClientType.COMPANY &&
                (dto.getCompanyIdentifier() == null || dto.getCompanyIdentifier().isBlank()))
            return ResponseEntity.badRequest().build();

        Client c = Client.builder()
                .type(dto.getType())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .companyIdentifier(dto.getCompanyIdentifier())
                .build();
        Client saved = service.create(c);
        return ResponseEntity.created(URI.create("/api/v1/clients/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> get(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto){
        Client changes = new Client();
        changes.setName(dto.getName());
        changes.setPhone(dto.getPhone());
        changes.setEmail(dto.getEmail());
        return ResponseEntity.ok(service.update(id, changes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}