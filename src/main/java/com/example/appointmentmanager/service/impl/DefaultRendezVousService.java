package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.RendezVousCreateRequest;
import com.example.appointmentmanager.exceptions.ApplicationException;
import com.example.appointmentmanager.model.RendezVous;
import com.example.appointmentmanager.repository.RendezVousRepository;
import com.example.appointmentmanager.service.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Transactional
    @Override
    public RendezVous create(RendezVousCreateRequest request) {

        if (request.dateRDV().minusHours(48).isBefore(LocalDateTime.now())) {
            throw new ApplicationException("Le rendez-vous doit se prendre au moins 48h en avance");
        }

        repository.findByRef(request.refRDV()).ifPresent(r -> {
            throw new ApplicationException("Un rendez-vous avec cette référence a déjà été enregistré");
        });

        var rendezVousTime = request.dateRDV().toLocalTime();
        var rendezVousDate = request.dateRDV().toLocalDate();

        var plageHoraire = plageHoraireService.findWithTimeBetweenDebutAndFin(rendezVousTime);
        var responsable = responsableService.findByReference(request.refResponsable());
        var departement = departementService.findByReference(request.refService());
        var client = clientService.findByReference(request.refClient());

        if (!responsable.getService().getId().equals(departement.getId())) {
            throw new ApplicationException(
                    "Le service spécifié ne correspond pas à celui du responsable indiqué pour le rendez-vous");
        }

        repository.findByServiceAndPlageAndDate(departement, plageHoraire, rendezVousDate).ifPresent((rdv) -> {
            throw new ApplicationException(
                    "Un rendez-vous a déjà été enregistré pour le service spécifié à cette date et à cette plage.");
        });

        repository.findByClientAndPlageAndDate(client, plageHoraire, rendezVousDate).ifPresent((rdv) -> {
            throw new ApplicationException(
                    "Le client spécifié est a déjà un rendez-vous enregistré à cette date à cette plage.");
        });


        var rendezVous = new RendezVous();

        rendezVous.setRef(request.refRDV());
        rendezVous.setMotif(request.motifRDV());
        rendezVous.setDateRendezVous(rendezVousDate);
        rendezVous.setPlageHoraire(plageHoraire);
        rendezVous.setResponsable(responsable);
        rendezVous.setDepartement(departement);
        rendezVous.setClient(client);

        return repository.save(rendezVous);
    }

}
