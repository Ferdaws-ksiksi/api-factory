package com.example.apifactory.repository;

import com.example.apifactory.domain.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT COALESCE(SUM(c.costAmount),0) FROM Contract c " +
            "WHERE c.client.id=:clientId AND (c.endDate IS NULL OR c.endDate > :now)")
    BigDecimal sumActiveContracts(@Param("clientId") Long clientId, @Param("now") LocalDate now);

    @Query("SELECT c FROM Contract c WHERE c.client.id=:clientId " +
            "AND (c.endDate IS NULL OR c.endDate > :now) " +
            "AND (:updatedAfter IS NULL OR c.updatedAt>=:updatedAfter)")
    Page<Contract> findActiveByClientAndUpdatedAfter(@Param("clientId") Long clientId,
                                                     @Param("now") LocalDate now,
                                                     @Param("updatedAfter") Instant updatedAfter,
                                                     Pageable pageable);
}