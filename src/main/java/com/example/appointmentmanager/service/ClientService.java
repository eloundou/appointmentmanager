package com.example.appointmentmanager.service;

import com.example.appointmentmanager.dto.ClientAddRequest;
import com.example.appointmentmanager.dto.ClientUpdateRequest;
import com.example.appointmentmanager.model.Client;

import java.util.List;

public interface ClientService {

    Client create(ClientAddRequest request);

    Client update(Long id, ClientUpdateRequest request);

    Client findById(Long id);

    List<Client> findAll();

}
