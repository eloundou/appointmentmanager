package com.example.appointmentmanager.service;

import com.example.appointmentmanager.model.PlageHoraire;

import java.time.LocalTime;
import java.util.List;

public interface PlageHoraireService {

    List<PlageHoraire> findAll();

    PlageHoraire findWithTimeBetweenDebutAndFin(LocalTime time);

}
