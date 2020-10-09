package com.example.parking.contractservice.Service;

import com.example.parking.contractservice.model.Client;
import com.example.parking.contractservice.repositories.ClientRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepo clientRepository;

    @Override
    public Client createClient(Client clientForSave) {
        return clientRepository.save(clientForSave);
    }



    @Override
    public List<Client> readAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Client readClient(UUID id) throws NotFoundException {
        Optional<Client> tempClient = clientRepository.findById(id);
        if(tempClient.isPresent())
            return clientRepository.findById(id).get();
        else
            throw new NotFoundException(String.format("Client with id %s dont exist",id));
    }

    @Override
    public  Client updateClientPayPrice(UUID id, double payPrice) throws NotFoundException{

        return clientRepository.save(readClient(id).setMonthPay(payPrice));
    }
    @Override
    public void deleteClient(UUID id) throws NotFoundException {
        clientRepository.delete(readClient(id));
    }


}
