package com.example.appointmentmanager.service;

import com.example.appointmentmanager.dto.ResponsableAddRequest;
import com.example.appointmentmanager.dto.ResponsableUpdateRequest;
import com.example.appointmentmanager.model.Responsable;

import java.util.List;

public interface ResponsableService {

    Responsable create(ResponsableAddRequest request);

    Responsable update(Long id, ResponsableUpdateRequest request);

    Responsable findById(Long id);

    List<Responsable> findAll();

}
