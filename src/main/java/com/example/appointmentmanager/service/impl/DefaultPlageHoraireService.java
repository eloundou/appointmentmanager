package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.PlageHoraire;
import com.example.appointmentmanager.repository.PlageHoraireRepository;
import com.example.appointmentmanager.service.PlageHoraireService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DefaultPlageHoraireService implements PlageHoraireService {

    private final PlageHoraireRepository plageHoraireRepository;

    public DefaultPlageHoraireService(PlageHoraireRepository plageHoraireRepository) {
        this.plageHoraireRepository = plageHoraireRepository;
    }

    @Override
    public List<PlageHoraire> findAll() {
        return plageHoraireRepository.findAll();
    }

    @Override
    public PlageHoraire findWithTimeBetweenDebutAndFin(LocalTime time) {
        return plageHoraireRepository.findWithTimeBetweenDebutAndFin(time).orElseThrow(
                () -> new ResourceNotFoundException("Aucune plage horaire trouvée pour l'heure " + time.getHour() + "h"));
    }

}
