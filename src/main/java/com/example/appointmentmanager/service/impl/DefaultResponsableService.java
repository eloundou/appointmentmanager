package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.ResponsableAddRequest;
import com.example.appointmentmanager.dto.ResponsableUpdateRequest;
import com.example.appointmentmanager.exceptions.ApplicationException;
import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Responsable;
import com.example.appointmentmanager.repository.ResponsableRepository;
import com.example.appointmentmanager.service.DepartementService;
import com.example.appointmentmanager.service.ResponsableService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultResponsableService implements ResponsableService {

    private final ResponsableRepository responsableRepository;

    private final DepartementService departementService;

    public DefaultResponsableService(ResponsableRepository responsableRepository,
                                     DepartementService departementService) {

        this.responsableRepository = responsableRepository;
        this.departementService = departementService;
    }

    @Transactional
    @Override
    public Responsable create(ResponsableAddRequest request) {

        responsableRepository.findByRef(request.ref()).ifPresent(r -> {
            throw new ApplicationException("Un responsable avec cette référence a déjà été enregistré");
        });

        var responsable = new Responsable();
        responsable.setRef(request.ref());
        responsable.setEmail(request.email());
        responsable.setTelephone(request.telephone());
        responsable.setNom(request.nom());
        responsable.setPrenom(request.prenom());

        responsable.setService(departementService.findByReference(request.refService()));

        return responsableRepository.save(responsable);
    }

    @Transactional
    @Override
    public Responsable update(Long id, ResponsableUpdateRequest request) {

        var responsable = findById(id);

        responsable.setEmail(request.email());
        responsable.setTelephone(request.telephone());
        responsable.setNom(request.nom());
        responsable.setPrenom(request.prenom());

        if (request.email() != null) responsable.setEmail(request.email());
        if (request.telephone() != null) responsable.setTelephone(request.telephone());
        if (request.nom() != null && !request.nom().isEmpty()) responsable.setNom(request.nom());
        if (request.prenom() != null) responsable.setPrenom(request.prenom());

        return responsableRepository.save(responsable);
    }

    @Override
    public Responsable findById(Long id) {
        return responsableRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le responsable avec l'identifiant " + id));
    }

    @Override
    public Responsable findByReference(String reference) {
        return responsableRepository.findByRef(reference).orElseThrow(
                () -> new ResourceNotFoundException("Impossible de trouver le responsable avec la référence " + reference));
    }

    @Override
    public List<Responsable> findAll() {
        return responsableRepository.findAll();
    }
}
