package com.consumer;

import com.config.MessagingConfig;
import com.example.parking.contractservice.Service.ClientService;
import com.example.parking.contractservice.Service.ContractService;
import com.example.parking.contractservice.Service.ParkingPlaceService;
import com.example.parking.contractservice.model.Client;
import com.example.parking.contractservice.model.Contract;
import com.example.parking.contractservice.model.ParkingPlace;
import com.example.parking.contractservice.model.temporaryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Consumer {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private final ParkingPlaceService parkingPlaceService;
    @Autowired
    private final ClientService clientService;
    @Autowired
    private final ContractService contractService;

    public Consumer(ParkingPlaceService parkingPlaceService, ClientService clientService, ContractService contractService) {
        this.parkingPlaceService = parkingPlaceService;
        this.clientService = clientService;
        this.contractService = contractService;
    }

    @RabbitListener(queues = MessagingConfig.createPP)
    public void createPP(ParkingPlace parkingPlace)
    {
        System.out.println( parkingPlaceService.createParkingPlace(parkingPlace));

    }
    @RabbitListener(queues = MessagingConfig.updatePP)
    public void changeStatus(int id) throws NotFoundException {
        System.out.println(parkingPlaceService.updateParkingPlaceStatus(id));
    }

    @RabbitListener(queues = MessagingConfig.createCLIENT)
    public void createClient(Client client)
    {
        clientService.createClient(client);
    }

    @RabbitListener(queues =  MessagingConfig.deleteCLIENT)
    public void deleteClient(UUID id) throws NotFoundException {
        clientService.deleteClient(id);
    }

    @RabbitListener(queues =  MessagingConfig.createCONTRACT)
    public void createContract(Contract contract) throws NotFoundException {
        contractService.createContract(contract);
    }

    @RabbitListener(queues = MessagingConfig.deleteCONTRACT)
    public void deleteContract(UUID id) throws NotFoundException {
        contractService.deleteContract(id);
    }

    @RabbitListener(queues = MessagingConfig.updateCONTRACT)
    public void updateContract(temporaryWrapper wrapper) throws NotFoundException {
        System.out.println(wrapper);
        contractService.addParkingPlace(wrapper.getId(),wrapper.getParkingplaceId());
    }

}
