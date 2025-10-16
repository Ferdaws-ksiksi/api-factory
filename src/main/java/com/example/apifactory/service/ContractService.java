package com.example.apifactory.service;
import com.example.apifactory.domain.*;
import com.example.apifactory.dto.ContractDTO;
import com.example.apifactory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/** Business logic for contracts. */
@Service @RequiredArgsConstructor
public class ContractService {
    private final ClientRepository clientRepo;
    private final ContractRepository contractRepo;

    public Contract create(UUID clientId, ContractDTO dto){
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        Contract c = new Contract();
        c.setClient(client);
        c.setStartDate(dto.getStartDate()==null?Instant.now():dto.getStartDate());
        c.setEndDate(dto.getEndDate());
        c.setCostAmount(dto.getCostAmount());
        return contractRepo.save(c);
    }

    public Contract updateCost(UUID clientId, UUID contractId, ContractDTO dto){
        Contract c = contractRepo.findById(contractId)
                .orElseThrow(() -> new NotFoundException("Contract not found"));
        if(!c.getClient().getId().equals(clientId))
            throw new NotFoundException("Contract does not belong to client");
        c.setCostAmount(dto.getCostAmount());
        return contractRepo.save(c);
    }

    public Page<Contract> listActive(UUID clientId, Instant updatedAfter, Pageable pageable){
        return contractRepo.findActiveByClientAndUpdatedAfter(clientId, Instant.now(), updatedAfter, pageable);
    }

    public BigDecimal sumActive(UUID clientId){ return contractRepo.sumActiveContracts(clientId, Instant.now()); }
}
