package com.example.apifactory.repository;

import com.example.apifactory.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> { }
