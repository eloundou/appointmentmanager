package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.dto.ResponsableAddRequest;
import com.example.appointmentmanager.dto.ResponsableUpdateRequest;
import com.example.appointmentmanager.model.Responsable;
import com.example.appointmentmanager.service.ResponsableService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsables")
public class ResponsableController {

    private final ResponsableService responsableService;

    public ResponsableController(ResponsableService responsableService) {
        this.responsableService = responsableService;
    }

    @PostMapping
    ResponseEntity<Responsable> create(@RequestBody @Valid ResponsableAddRequest request) {
        return ResponseEntity.ok().body(responsableService.create(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<Responsable> update(@PathVariable("id") Long id,
                                       @RequestBody @Valid ResponsableUpdateRequest request) {

        return ResponseEntity.ok().body(responsableService.update(id, request));
    }

    @GetMapping("/{id}")
    ResponseEntity<Responsable> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(responsableService.findById(id));
    }

    @GetMapping("/all")
    ResponseEntity<List<Responsable>> get() {
        return ResponseEntity.ok().body(responsableService.findAll());
    }

}
