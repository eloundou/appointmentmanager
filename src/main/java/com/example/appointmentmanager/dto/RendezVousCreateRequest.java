package com.example.appointmentmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RendezVousCreateRequest(
        @NotBlank(message = "La référence du client est obligatoire")
        String refClient,

        @NotBlank(message = "La référence du rendez-vous est obligatoire")
        String refRDV,

        @NotBlank(message = "La référence du service est obligatoire")
        String refService,

        @NotBlank(message = "La référence du responsable est obligatoire")
        String refResponsable,

        @NotNull(message = "La date du rendez-vous est obligatoire")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateRDV,

        @NotBlank(message = "Le motif du rendez-vous est obligatoire")
        String motifRDV
) {
}
