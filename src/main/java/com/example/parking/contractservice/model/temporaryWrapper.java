package com.example.parking.contractservice.model;

import lombok.Data;

import java.util.UUID;
@Data
public class temporaryWrapper {

    private int parkingplaceId;
    private UUID id;

    public int getParkingplaceId() {
        return parkingplaceId;
    }

    public void setParkingplaceId(int parkingplaceId) {
        this.parkingplaceId = parkingplaceId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public temporaryWrapper(int parkingplaceId, UUID id) {
        this.parkingplaceId = parkingplaceId;
        this.id = id;
    }

    @Override
    public String toString() {
        return "temporaryWrapper{" +
                "parkingplaceId=" + parkingplaceId +
                ", id=" + id +
                '}';
    }
}
