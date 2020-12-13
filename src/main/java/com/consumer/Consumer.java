package com.consumer;

import com.config.MessagingConfig;
import com.example.parking.contractservice.Service.ParkingPlaceService;
import com.example.parking.contractservice.model.ParkingPlace;
import javassist.NotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private final ParkingPlaceService parkingPlaceService;

    public Consumer(ParkingPlaceService parkingPlaceService) {
        this.parkingPlaceService = parkingPlaceService;
    }

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(ParkingPlace parkingPlace)
    {
        System.out.println( parkingPlaceService.createParkingPlace(parkingPlace));

    }
    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void changestatus(int id) throws NotFoundException {
        System.out.println(parkingPlaceService.updateParkingPlaceStatus(id));
    }

}
