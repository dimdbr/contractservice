package com.example.parking.contractservice.web.interceptors.controllers;

import com.config.MessagingConfig;
import com.example.parking.contractservice.Service.ClientService;
import com.example.parking.contractservice.model.Client;
import javassist.NotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {

        this.clientService = clientService;
    }

    @PostMapping()
    public String create(@RequestBody Client client)
    {
        template.convertAndSend(MessagingConfig.EXCHANGECreateClient,MessagingConfig.ROUTING_KEY,client);
        return "client created";
        //return ResponseEntity.ok(clientService.createClient(client));
    }

    @GetMapping()
    public ResponseEntity<List<Client>> read()
    {
        return ResponseEntity.ok(clientService.readAllClients());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> read(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(clientService.readClient(id));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(name = "id") UUID id) throws NotFoundException {
        template.convertAndSend(MessagingConfig.EXCHANGEDeleteClient,MessagingConfig.ROUTING_KEY,id);
        //clientService.deleteClient(id);
        //return ResponseEntity.noContent().build();
    }

}
