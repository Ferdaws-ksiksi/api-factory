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
import java.time.LocalDate;

/** Business logic for contracts. */
@Service
@RequiredArgsConstructor
public class ContractService {
    private final ClientRepository clientRepo;
    private final ContractRepository contractRepo;

    public Contract create(Long clientId, ContractDTO dto){
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        Contract c = new Contract();
        c.setClient(client);
        c.setStartDate(dto.getStartDate() == null ? LocalDate.now() : dto.getStartDate());
        c.setEndDate(dto.getEndDate());
        c.setCostAmount(dto.getCostAmount());
        return contractRepo.save(c);
    }

    public Contract updateCost(Long clientId, Long contractId, ContractDTO dto){
        Contract c = contractRepo.findById(contractId)
                .orElseThrow(() -> new NotFoundException("Contract not found"));
        if(!c.getClient().getId().equals(clientId))
            throw new NotFoundException("Contract does not belong to client");
        c.setCostAmount(dto.getCostAmount());
        return contractRepo.save(c);
    }

    public Page<Contract> listActive(Long clientId, Instant updatedAfter, Pageable pageable){
        return contractRepo.findActiveByClientAndUpdatedAfter(clientId, LocalDate.now(), updatedAfter, pageable);
    }

    public BigDecimal sumActive(Long clientId){
        return contractRepo.sumActiveContracts(clientId, LocalDate.now());
    }
}