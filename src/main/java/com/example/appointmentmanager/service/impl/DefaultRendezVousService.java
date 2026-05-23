package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.RendezVousCreateRequest;
import com.example.appointmentmanager.model.RendezVous;
import com.example.appointmentmanager.repository.RendezVousRepository;
import com.example.appointmentmanager.service.*;
import org.springframework.stereotype.Service;

@Service
public class DefaultRendezVousService implements RendezVousService {

    private final RendezVousRepository repository;

    private final DepartementService departementService;
    private final ResponsableService responsableService;
    private final ClientService clientService;
    private final PlageHoraireService plageHoraireService;

    public DefaultRendezVousService(RendezVousRepository repository,
                                    DepartementService departementService,
                                    ResponsableService responsableService,
                                    ClientService clientService,
                                    PlageHoraireService plageHoraireService) {

        this.repository = repository;
        this.departementService = departementService;
        this.responsableService = responsableService;
        this.clientService = clientService;
        this.plageHoraireService = plageHoraireService;
    }

    @Override
    public RendezVous create(RendezVousCreateRequest request) {

        var rendezVousTime = request.dateRDV().toLocalTime();

        var rendezVous = new RendezVous();

        rendezVous.setRef(request.refRDV());
        rendezVous.setMotif(request.motifRDV());

        rendezVous.setPlageHoraire(plageHoraireService.findWithTimeBetweenDebutAndFin(rendezVousTime));
        rendezVous.setResponsable(responsableService.findByReference(request.refResponsable()));
        rendezVous.setDepartement(departementService.findByReference(request.refService()));
        rendezVous.setClient(clientService.findByReference(request.refClient()));

        return repository.save(rendezVous);
    }

}
