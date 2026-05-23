package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.ResponsableAddRequest;
import com.example.appointmentmanager.dto.ResponsableUpdateRequest;
import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Responsable;
import com.example.appointmentmanager.repository.ResponsableRepository;
import com.example.appointmentmanager.service.DepartementService;
import com.example.appointmentmanager.service.ResponsableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultResponsableService implements ResponsableService {

    private final ResponsableRepository departementRepository;

    private final DepartementService departementService;

    public DefaultResponsableService(ResponsableRepository departementRepository,
                                     DepartementService departementService) {

        this.departementRepository = departementRepository;
        this.departementService = departementService;
    }

    @Override
    public Responsable create(ResponsableAddRequest request) {

        var responsable = new Responsable();

        responsable.setRef(request.ref());
        responsable.setEmail(request.email());
        responsable.setTelephone(request.telephone());
        responsable.setNom(request.nom());
        responsable.setPrenom(request.prenom());
        responsable.setService(departementService.findById(request.serviceId()));

        return departementRepository.save(responsable);
    }

    @Override
    public Responsable update(Long id, ResponsableUpdateRequest request) {

        var responsable = findById(id);

        responsable.setEmail(request.email());
        responsable.setTelephone(request.telephone());
        responsable.setNom(request.nom());
        responsable.setPrenom(request.prenom());

        return departementRepository.save(responsable);
    }

    @Override
    public Responsable findById(Long id) {
        return departementRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le responsable avec l'identifiant " + id));
    }

    @Override
    public Responsable findByReference(String reference) {
        return departementRepository.findByRef(reference).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le responsable avec la référence " + reference));
    }

    @Override
    public List<Responsable> findAll() {
        return departementRepository.findAll();
    }
}
