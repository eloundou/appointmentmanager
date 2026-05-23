package com.example.appointmentmanager.service;

import com.example.appointmentmanager.model.Departement;

import java.util.List;

public interface DepartementService {

    /*Departement create(DepartementAddRequest request);

    Departement update(Long id, DepartementUpdateRequest request);

    Departement findById(Long id);*/

    Departement findByReference(String reference);

    List<Departement> findAll();

}
