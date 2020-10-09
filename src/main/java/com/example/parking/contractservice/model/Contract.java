package com.example.parking.contractservice.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Entity
@Table(schema ="contract_schema",name = "contracts")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Contract {
    @Id
    @Column(name = "id",updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID ContractId;
    @Column(name = "clientid")
    private UUID ClientId;
    @Type(type = "list-array")
    @Column(name = "registeredcars")
    private List<String> registeredCars;

    public Contract(UUID contractId, UUID clientId, List<String> registeredCars) {
        ContractId = contractId;
        ClientId = clientId;
        this.registeredCars = registeredCars;
    }
    public Contract()
    {}

    public Contract(UUID clientId, String carName)
    {   this.ContractId= UUID.randomUUID();
        this.ClientId= clientId;

        this.registeredCars.add(carName);
    }
    public Contract(UUID clientId, List<String> carNames)
    {
        this.ContractId=UUID.randomUUID();
        this.ClientId= clientId;

        this.registeredCars=carNames;
    }

    public UUID getContractId() {
        return ContractId;
    }

    public UUID getClientId() {
        return ClientId;
    }



    public List<String> getRegisteredCars() {
        return registeredCars;
    }

    public void setClientId(UUID clientId) {
        ClientId = clientId;
    }

    public Contract removeClientCar(String carName)
    {
        if(this.registeredCars.contains(carName))
        { this.registeredCars.remove(carName);
        }
        return this;
    }

    public Contract addClientCar(String carName)
    {
        if(this.registeredCars.contains(carName)) {
        }
        else
        {
            this.registeredCars.add(carName);
        }
        return this;
    }

}
