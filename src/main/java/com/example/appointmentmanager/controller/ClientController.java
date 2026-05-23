package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.dto.ClientAddRequest;
import com.example.appointmentmanager.dto.ClientUpdateRequest;
import com.example.appointmentmanager.model.Client;
import com.example.appointmentmanager.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    ResponseEntity<Client> create(@RequestBody @Valid ClientAddRequest request) {
        return ResponseEntity.ok().body(clientService.create(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<Client> update(@PathVariable("id") Long id, @RequestBody @Valid ClientUpdateRequest request) {
        return ResponseEntity.ok().body(clientService.update(id, request));
    }

    @GetMapping("/{id}")
    ResponseEntity<Client> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(clientService.findById(id));
    }

    @GetMapping("/all")
    ResponseEntity<List<Client>> get() {
        return ResponseEntity.ok().body(clientService.findAll());
    }

}
