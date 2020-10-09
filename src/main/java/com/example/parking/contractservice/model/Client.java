package com.example.parking.contractservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(schema = "contract_schema",name = "clients")
public class Client {
    @Id
    @Column(name = "id",updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID clientId;
    @Column(name = "name")
    private  String name;
    @Column(name = "monthpay")
    private  double monthPay;


    public Client(String name) {
        this.clientId=UUID.randomUUID();
        this.name = name;
        this.monthPay=0;
    }

    public Client() {

    }

    public UUID getClientID() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public double getMonthPay() {
        return monthPay;
    }

    public Client setMonthPay(double monthPay) {
        this.monthPay = monthPay;
        return this;
    }
}
