package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.repository.DepartementRepository;
import com.example.appointmentmanager.service.DepartementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultDepartementService implements DepartementService {

    private final DepartementRepository departementRepository;

    public DefaultDepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @Override
    public Departement findByReference(String reference) {
        return departementRepository.findByRef(reference).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le service avec la référence " + reference));
    }

    @Override
    public List<Departement> findAll() {
        return departementRepository.findAll();
    }
}
