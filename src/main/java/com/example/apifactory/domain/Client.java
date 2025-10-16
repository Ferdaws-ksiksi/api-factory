package com.example.apifactory.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

/** JPA entity for a client (person or company). */
@Entity
@Table(name = "client")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Client {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientType type;

    @Column(nullable = false)
    private String name;

    private String phone;
    private String email;
    private LocalDate birthDate;
    private String companyIdentifier;
    private Boolean deleted = false;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contracts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
    @PreUpdate
    public void preUpdate() { updatedAt = Instant.now(); }
}
