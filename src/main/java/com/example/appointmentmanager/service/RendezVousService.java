package com.example.appointmentmanager.service;

import com.example.appointmentmanager.dto.RendezVousCreateRequest;
import com.example.appointmentmanager.model.RendezVous;

public interface RendezVousService {
    RendezVous create(RendezVousCreateRequest request);
}
