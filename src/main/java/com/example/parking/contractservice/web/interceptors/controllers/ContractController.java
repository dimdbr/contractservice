package com.example.parking.contractservice.web.interceptors.controllers;

import com.config.MessagingConfig;
import com.example.parking.contractservice.Service.ContractService;
import com.example.parking.contractservice.model.Contract;
import com.example.parking.contractservice.model.temporaryWrapper;
import javassist.NotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/contracts")
public class ContractController {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping()
    public String create (@RequestBody Contract contract) throws NotFoundException
    {
        template.convertAndSend(MessagingConfig.EXCHANGECreateContract,MessagingConfig.ROUTING_KEY,contract);
        return "Contract created";
        //return ResponseEntity.ok(contractService.createContract(contract));
    }

    @GetMapping()
    public ResponseEntity<?> read()
    {
        return ResponseEntity.ok(contractService.readAllContracts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> read(@PathVariable UUID id) throws NotFoundException
    {
        return ResponseEntity.ok(contractService.readContract(id));
    }
    @GetMapping(value = "/findbyclient/{clientid}")
    public ResponseEntity<?> readByClientId(@PathVariable UUID clientid) throws NotFoundException
    {
        return ResponseEntity.ok(contractService.readByClientId(clientid));
    }
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable UUID id)  throws NotFoundException
    {
        template.convertAndSend(MessagingConfig.EXCHANGEDeleteContract,MessagingConfig.ROUTING_KEY,id);
        //contractService.deleteContract(id);
        //return ResponseEntity.noContent().build();
    }



    @PutMapping(value = "/{id}/addcar/{carname}")
    public ResponseEntity<?> addCar(@PathVariable(name = "id") UUID id,@PathVariable(name = "carname") String carname) throws NotFoundException
    {
        //template.convertAndSend(MessagingConfig.EXCHANGEUpdateContract,MessagingConfig.ROUTING_KEY);
        return ResponseEntity.ok(contractService.addClientCar(id,carname));
    }

    @PutMapping(value = "/{id}/delcar/{carname}")
    public ResponseEntity<?> removeCar(@PathVariable UUID id,@PathVariable String carname) throws NotFoundException
    {
        return ResponseEntity.ok(contractService.removeClientCar(id,carname));
    }

    @PutMapping(value = "/{id}/addparkplace/{parkingplaceid}")
    public void addParkPlace(@PathVariable UUID id, @PathVariable int parkingplaceid)  throws NotFoundException
    {   temporaryWrapper wrapper = new temporaryWrapper(parkingplaceid,id);
        System.out.println(wrapper);
        template.convertAndSend(MessagingConfig.EXCHANGEUpdateContract,MessagingConfig.ROUTING_KEY,wrapper);
        //return ResponseEntity.ok(contractService.addParkingPlace(id,parkingplaceid));
    }
    @PutMapping(value = "/{id}/removeparkplace/{parkingplaceid}")
    public ResponseEntity<?> removeParkPlace(@PathVariable UUID id, @PathVariable int parkingplaceid)  throws NotFoundException
    {
        return ResponseEntity.ok(contractService.removeParkingPlace(id,parkingplaceid));
    }
}
