package com.example.parking.contractservice.repositories;

import com.example.parking.contractservice.model.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ParkingPlaceRepo extends JpaRepository<ParkingPlace,Integer> {
    @Query(value = "select * from contract_schema.parkingplaces where contractid = ?1",nativeQuery = true)
    List<ParkingPlace> getParkingPlaceByContractId(UUID contractId);
    @Query(value = "select * from contract_schema.parkingplaces ",nativeQuery = true)
    List<ParkingPlace> getParkingPlaces();
}
