package com.example.parking.contractservice.repositories;

import com.example.parking.contractservice.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ContractRepo extends JpaRepository<Contract, UUID> {
    @Query( value = "SELECT * FROM contract_schema.contracts where clientid=?1",nativeQuery = true)
    Contract findByClientId(UUID clientId);
}
