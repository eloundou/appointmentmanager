package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.dto.DepartementAddRequest;
import com.example.appointmentmanager.dto.DepartementUpdateRequest;
import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.service.DepartementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class DepartmentController {

    private final DepartementService departementService;

    public DepartmentController(DepartementService clientService) {
        this.departementService = clientService;
    }

    @PostMapping
    ResponseEntity<Departement> create(@RequestBody @Valid DepartementAddRequest addAccountRequest) {
        return ResponseEntity.ok().body(departementService.create(addAccountRequest));
    }

    @PutMapping("/{id}")
    ResponseEntity<Departement> update(@PathVariable("id") Long id, @RequestBody @Valid DepartementUpdateRequest updateRequest) {
        return ResponseEntity.ok().body(departementService.update(id, updateRequest));
    }

    @GetMapping("/{id}")
    ResponseEntity<Departement> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(departementService.findById(id));
    }

    @GetMapping("/all")
    ResponseEntity<List<Departement>> get() {
        return ResponseEntity.ok().body(departementService.findAll());
    }

}
