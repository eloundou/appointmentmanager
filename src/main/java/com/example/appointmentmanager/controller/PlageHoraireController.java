package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.model.PlageHoraire;
import com.example.appointmentmanager.service.PlageHoraireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plage-horaires")
public class PlageHoraireController {

    private final PlageHoraireService plageHoraireService;

    public PlageHoraireController(PlageHoraireService plageHoraireService) {
        this.plageHoraireService = plageHoraireService;
    }

    @GetMapping("/all")
    ResponseEntity<List<PlageHoraire>> get() {
        return ResponseEntity.ok().body(plageHoraireService.findAll());
    }

}
