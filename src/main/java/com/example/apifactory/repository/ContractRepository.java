package com.example.apifactory.repository;

import com.example.apifactory.domain.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    @Query("SELECT COALESCE(SUM(c.costAmount),0) FROM Contract c " +
            "WHERE c.client.id=:clientId AND (c.endDate IS NULL OR c.endDate > :now)")
    BigDecimal sumActiveContracts(@Param("clientId") UUID clientId, @Param("now") Instant now);

    @Query("SELECT c FROM Contract c WHERE c.client.id=:clientId " +
            "AND (c.endDate IS NULL OR c.endDate > :now) " +
            "AND (:updatedAfter IS NULL OR c.lastModifiedDate>=:updatedAfter)")
    Page<Contract> findActiveByClientAndUpdatedAfter(@Param("clientId") UUID clientId,
                                                     @Param("now") Instant now,
                                                     @Param("updatedAfter") Instant updatedAfter,
                                                     Pageable pageable);
}
