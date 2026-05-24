package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.ClientAddRequest;
import com.example.appointmentmanager.dto.ClientUpdateRequest;
import com.example.appointmentmanager.exceptions.ApplicationException;
import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Client;
import com.example.appointmentmanager.repository.ClientRepository;
import com.example.appointmentmanager.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultClientService implements ClientService {

    private final ClientRepository clientRepository;

    public DefaultClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public Client create(ClientAddRequest request) {

        clientRepository.findByRef(request.ref()).ifPresent(c -> {
            throw new ApplicationException("Un client avec cette référence a déjà été enregistré");
        });

        clientRepository.findByEmail(request.email()).ifPresent(r -> {
            throw new ApplicationException("Un client avec cette adresse email a déjà été enregistré");
        });

        var client = new Client();
        client.setRef(request.ref());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNom(request.nom());
        client.setPrenom(request.prenom());

        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public Client update(Long id, ClientUpdateRequest request) {

        var client = findById(id);

        if (request.email() != null) client.setEmail(request.email());
        if (request.telephone() != null) client.setTelephone(request.telephone());
        if (request.nom() != null && !request.nom().isEmpty()) client.setNom(request.nom());
        if (request.prenom() != null) client.setPrenom(request.prenom());

        if (request.email() != null) {
            clientRepository.findByEmail(request.email()).ifPresent(c -> {
                if (!c.getId().equals(client.getId()))
                    throw new ApplicationException("Un client avec cette adresse email a déjà été enregistré");
            });
        }

        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le client avec l'identifiant " + id));
    }

    @Override
    public Client findByReference(String reference) {
        return clientRepository.findByRef(reference).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le client avec la référence " + reference));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
