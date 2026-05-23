package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.service.DepartementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
public class DepartmentController {

    private final DepartementService departementService;

    public DepartmentController(DepartementService departementService) {
        this.departementService = departementService;
    }

    /*@PostMapping
    ResponseEntity<Departement> create(@RequestBody @Valid DepartementAddRequest request) {
        return ResponseEntity.ok().body(departementService.create(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<Departement> update(@PathVariable("id") Long id,
                                       @RequestBody @Valid DepartementUpdateRequest request) {
        return ResponseEntity.ok().body(departementService.update(id, request));
    }

    @GetMapping("/{id}")
    ResponseEntity<Departement> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(departementService.findById(id));
    }*/

    @GetMapping("/all")
    ResponseEntity<List<Departement>> get() {
        return ResponseEntity.ok().body(departementService.findAll());
    }

}
