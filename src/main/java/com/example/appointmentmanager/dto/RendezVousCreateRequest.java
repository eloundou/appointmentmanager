package com.example.appointmentmanager.dto;

import java.time.LocalDateTime;

public record RendezVousCreateRequest(String refClient,
                                      String refRDV,
                                      String refService,
                                      String refResponsable,
                                      LocalDateTime dateRDV,
                                      String motifRDV) {
}
