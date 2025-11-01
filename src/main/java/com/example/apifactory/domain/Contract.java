package com.example.apifactory.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "cost_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal costAmount;

    @Column(name = "updated_at", nullable = false)
    @JsonIgnore  // updatedAt should NOT be exposed in API per specs
    private Instant updatedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (startDate == null) startDate = LocalDate.now();
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}