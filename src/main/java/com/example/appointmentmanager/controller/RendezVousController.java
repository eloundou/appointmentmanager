package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.dto.RendezVousCreateRequest;
import com.example.appointmentmanager.model.RendezVous;
import com.example.appointmentmanager.service.RendezVousService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @PostMapping
    ResponseEntity<RendezVous> create(@RequestBody @Valid RendezVousCreateRequest request) {
        return ResponseEntity.ok().body(rendezVousService.create(request));
    }

    @GetMapping("/all")
    ResponseEntity<List<RendezVous>> get() {
        return ResponseEntity.ok().body(rendezVousService.findAll());
    }

}
