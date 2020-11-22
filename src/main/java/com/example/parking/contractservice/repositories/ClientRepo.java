package com.example.parking.contractservice.repositories;

import com.example.parking.contractservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ClientRepo extends JpaRepository<Client, UUID> {
}
