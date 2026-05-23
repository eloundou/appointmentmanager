package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.ClientAddRequest;
import com.example.appointmentmanager.dto.ClientUpdateRequest;
import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Client;
import com.example.appointmentmanager.repository.ClientRepository;
import com.example.appointmentmanager.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultClientService implements ClientService {

    private final ClientRepository clientRepository;

    public DefaultClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(ClientAddRequest request) {

        Client client = new Client();
        client.setRef(request.ref());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNom(request.nom());
        client.setPrenom(request.prenom());

        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, ClientUpdateRequest request) {

        Client client = findById(id);

        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNom(request.nom());
        client.setPrenom(request.prenom());

        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le client avec l'id " + id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
