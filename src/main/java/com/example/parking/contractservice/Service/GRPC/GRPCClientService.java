package com.example.parking.contractservice.Service.GRPC;

import com.contractservice.grpc.ContractServiceGrpc;
import com.contractservice.grpc.ContractServiceOuterClass;
import com.example.parking.contractservice.model.Client;
import com.example.parking.contractservice.model.Contract;
import com.example.parking.contractservice.repositories.ClientRepo;
import com.example.parking.contractservice.repositories.ContractRepo;
import com.example.parking.contractservice.repositories.ParkingPlaceRepo;
import com.google.protobuf.GeneratedMessageV3;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@GRpcService
@Service
public class GRPCClientService extends ContractServiceGrpc.ContractServiceImplBase {

    @Autowired
    private ClientRepo clientRepository;
    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private ParkingPlaceRepo parkingPlaceRepo;

    @Override
    public void grpcGetClient(ContractServiceOuterClass.UUID request, StreamObserver<ContractServiceOuterClass.Client> responseObserver) {

        System.out.println("try with it");
        System.out.println("Try getting client");
        String string_id = request.getValue();
        UUID uuid_id = UUID.fromString(string_id);
        System.out.println("getting id : " + uuid_id);
        Optional<Client> tempClient = clientRepository.findById(uuid_id);
        Client client;
        ContractServiceOuterClass.Client.Builder response =
                ContractServiceOuterClass.Client.newBuilder();
        if (tempClient.isPresent()) {
            client = clientRepository.findById(uuid_id).get();
            response.setName(client.getName());
            response.setMonthPay(client.getMonthPay());
            response.setId(client.getClientID().toString());
        } else {
            response.setName("there is no such client");
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcGetAllClients(ContractServiceOuterClass.Empty request, StreamObserver<ContractServiceOuterClass.Clients> responseObserver) {
        List<Client> clients = clientRepository.findAll();
        ContractServiceOuterClass.Clients.Builder clientsResponse =
                ContractServiceOuterClass.Clients.newBuilder();
        for (Client c : clients) {
            ContractServiceOuterClass.Client.Builder clientToList =
                    ContractServiceOuterClass.Client.newBuilder();
            clientToList.setName(c.getName());
            clientToList.setMonthPay(c.getMonthPay());
            clientToList.setId(c.getClientID().toString());
            clientsResponse.addClients(clientToList);

        }
        responseObserver.onNext(clientsResponse.build());
        responseObserver.onCompleted();

    }

    @Override
    public void grpcAddClient(ContractServiceOuterClass.CreateClient request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {
        String clientName = request.getName();
        Client client = new Client(clientName);
        clientRepository.save(client);
        ContractServiceOuterClass.Empty.Builder response =
                ContractServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcDeleteClient(ContractServiceOuterClass.UUID request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {

        String clientId = request.getValue();
        UUID clientUuid = UUID.fromString(clientId);
        clientRepository.deleteById(clientUuid);
        ContractServiceOuterClass.Empty.Builder response =
                ContractServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcUpdateClient(ContractServiceOuterClass.UpdateClient request, StreamObserver<ContractServiceOuterClass.Client> responseObserver) {
        String clientId = request.getId();
        double monthpay = request.getMonthPay();
        UUID clientUuid = UUID.fromString(clientId);
        clientRepository.save(clientRepository.
                findById(clientUuid).
                get().
                setMonthPay(monthpay));
        Optional<Client> tempClient = clientRepository.findById(clientUuid);
        Client client;
        ContractServiceOuterClass.Client.Builder response =
                ContractServiceOuterClass.Client.newBuilder();
        if (tempClient.isPresent()) {
            client = clientRepository.findById(clientUuid).get();
            response.setName(client.getName());
            response.setMonthPay(client.getMonthPay());
            response.setId(client.getClientID().toString());
        } else {
            response.setName("there is no such client");
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void grpcGetContract(ContractServiceOuterClass.UUID request, StreamObserver<ContractServiceOuterClass.Contract> responseObserver) {
        String string_id = request.getValue();
        UUID uuid_id = UUID.fromString(string_id);
        Optional<Contract> tempContracts = contractRepo.findById(uuid_id);
        Contract contract;
        ContractServiceOuterClass.Contract.Builder response =
                ContractServiceOuterClass.Contract.newBuilder();
        if (tempContracts.isPresent()) {
            contract = contractRepo.findById(uuid_id).get();
            response.setId(contract.getContractId().toString());
            response.setClientId(contract.getContractId().toString());
            response.addReqisteredCars(contract.getRegisteredCars().toString());
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcGetAllContracts(ContractServiceOuterClass.Empty request, StreamObserver<ContractServiceOuterClass.Contracts> responseObserver) {
        List<Contract> contracts = contractRepo.findAll();
        ContractServiceOuterClass.Contracts.Builder contractsResponse =
                ContractServiceOuterClass.Contracts.newBuilder();
        for (Contract c : contracts) {
            ContractServiceOuterClass.Contract.Builder contractsToList =
                    ContractServiceOuterClass.Contract.newBuilder();
            contractsToList.setId(c.getContractId().toString());
            contractsToList.setClientId(c.getClientId().toString());
            contractsToList.addReqisteredCars(c.getRegisteredCars().toString());
            contractsResponse.addContracts(contractsToList);

        }
        responseObserver.onNext(contractsResponse.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcAddContract(ContractServiceOuterClass.createContract request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {

        String clientId = request.getClientId();
        UUID clientUuid = UUID.fromString(clientId);
        String registeredCars = request.getCarName();
        Contract contract = new Contract(clientUuid, registeredCars);
        contractRepo.save(contract);
        ContractServiceOuterClass.Empty.Builder response =
                ContractServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcAddParkingPlace(ContractServiceOuterClass.changeClientPlace request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {
        super.grpcAddParkingPlace(request, responseObserver);
    }

    @Override
    public void grpcRemoveParkingPlace(ContractServiceOuterClass.changeClientPlace request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {
        super.grpcRemoveParkingPlace(request, responseObserver);
    }

    @Override
    public void grpcAddClientCar(ContractServiceOuterClass.changeClientCar request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {
        String id = request.getId();
        String carName = request.getCarName();
        UUID uuid = UUID.fromString(id);
        contractRepo.save(contractRepo.
                findById(uuid).
                get().
                addClientCar(carName));
        ContractServiceOuterClass.Empty.Builder response =
                ContractServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcRemoveClientCar(ContractServiceOuterClass.changeClientCar request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {
        String id = request.getId();
        String carName = request.getCarName();
        UUID uuid = UUID.fromString(id);
        contractRepo.save(contractRepo.findById(uuid).get().removeClientCar(carName));
        ContractServiceOuterClass.Empty.Builder response =
                ContractServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void grpcDeleteContract(ContractServiceOuterClass.UUID request, StreamObserver<ContractServiceOuterClass.Empty> responseObserver) {
        String id = request.getValue();
        UUID uuid = UUID.fromString(id);
        contractRepo.deleteById(uuid);
        ContractServiceOuterClass.Empty.Builder response =
                ContractServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
