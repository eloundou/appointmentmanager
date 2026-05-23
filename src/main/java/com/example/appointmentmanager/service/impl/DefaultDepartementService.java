package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.DepartementAddRequest;
import com.example.appointmentmanager.dto.DepartementUpdateRequest;
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
    public Departement create(DepartementAddRequest request) {

        Departement departement = new Departement();
        departement.setRef(request.ref());
        departement.setNom(request.nom());

        return departementRepository.save(departement);
    }

    @Override
    public Departement update(Long id, DepartementUpdateRequest request) {

        Departement departement = findById(id);

        departement.setNom(request.nom());

        return departementRepository.save(departement);
    }

    @Override
    public Departement findById(Long id) {
        return departementRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le service avec l'identifiant " + id));
    }

    @Override
    public List<Departement> findAll() {
        return departementRepository.findAll();
    }
}
