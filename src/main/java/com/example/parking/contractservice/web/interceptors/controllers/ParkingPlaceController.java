package com.example.parking.contractservice.web.interceptors.controllers;

import com.config.MessagingConfig;
import com.example.parking.contractservice.Service.ParkingPlaceService;
import com.example.parking.contractservice.model.ParkingPlace;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/parkingplaces")
public class ParkingPlaceController {


    @Autowired
    private RabbitTemplate template;
    @Autowired
    private final ParkingPlaceService parkingPlaceService;

    public ParkingPlaceController(ParkingPlaceService parkingPlaceService) {
        this.parkingPlaceService = parkingPlaceService;
    }

    @PostMapping()
    public String create(@RequestBody ParkingPlace parkingPlace) throws JsonProcessingException {

        //String json = objectMapper.writeValueAsString(parkingPlaceService.createParkingPlace(parkingPlace));
        template.convertAndSend(MessagingConfig.EXCHANGE,MessagingConfig.ROUTING_KEY,parkingPlace);
        return "Added parking place";
    }

    @GetMapping()
    public ResponseEntity<List<ParkingPlace>> read() {
        return ResponseEntity.ok(parkingPlaceService.readParkingPlaces());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ParkingPlace> read(@PathVariable(name = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(parkingPlaceService.readParkingPlace(id));
    }

    @PutMapping(value = "/{id}")
    public String update(@PathVariable(name = "id") int id) throws NotFoundException {
        template.convertAndSend(MessagingConfig.EXCHANGE,MessagingConfig.ROUTING_KEY,id);
        return "Status changed successfully";
        //return ResponseEntity.ok(parkingPlaceService.updateParkingPlaceStatus(id));
    }
}
