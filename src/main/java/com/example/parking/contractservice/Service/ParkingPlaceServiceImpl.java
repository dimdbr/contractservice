package com.example.parking.contractservice.Service;

import com.example.parking.contractservice.model.ParkingPlace;
import com.example.parking.contractservice.repositories.ParkingPlaceRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ParkingPlaceServiceImpl implements ParkingPlaceService {

    @Autowired
    private ParkingPlaceRepo parkingPlaceRepository;

    @Override
    public ParkingPlace createParkingPlace(ParkingPlace parkingPlace) {
        return parkingPlaceRepository.save(parkingPlace);
    }

    @Override
    public List<ParkingPlace> readParkingPlaces() {
        return parkingPlaceRepository.getParkingPlaces();
    }

    @Override
    public ParkingPlace readParkingPlace(int id) throws NotFoundException {
        Optional<ParkingPlace> tempPP = parkingPlaceRepository.findById(id);
        if(tempPP.isPresent())
        {
            return parkingPlaceRepository.findById(id).get();
        }
        else throw new NotFoundException(String.format("ParkingPlace with id % dont exist",id));
    }

    @Override
    public ParkingPlace updateParkingPlaceStatus(int id) throws NotFoundException
    {
        return parkingPlaceRepository.save(readParkingPlace(id).changeStatus());
    }

    @Override
    public List<ParkingPlace> readParkingPlacesByContractId(UUID contractId) throws NotFoundException {
        return parkingPlaceRepository.getParkingPlaceByContractId(contractId);
    }

    @Override
    public ParkingPlace addParkingPlaceToContract(UUID contractId,int id) throws NotFoundException{
        return parkingPlaceRepository.save(readParkingPlace(id).addToContract(contractId));
    }

    @Override
    public ParkingPlace removeParkingPlaceFromContract(UUID contractId, int id) throws NotFoundException {
        return parkingPlaceRepository.save(readParkingPlace(id).removeFromContract(contractId));
    }
}
