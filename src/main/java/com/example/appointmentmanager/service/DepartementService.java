package com.example.appointmentmanager.service;

import com.example.appointmentmanager.model.Departement;

import java.util.List;

public interface DepartementService {

    Departement findByReference(String reference);

    List<Departement> findAll();

}
