package com.example.apifactory.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/** JPA entity for a contract belonging to a client. */
@Entity
@Table(name = "contract")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Contract {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private Instant startDate = Instant.now();
    private Instant endDate;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal costAmount;
    private Instant lastModifiedDate = Instant.now();
    private Instant createdAt = Instant.now();

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (startDate == null) startDate = Instant.now();
        createdAt = Instant.now();
        lastModifiedDate = Instant.now();
    }
    @PreUpdate
    public void preUpdate() { lastModifiedDate = Instant.now(); }
}
