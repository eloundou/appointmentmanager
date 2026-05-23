package com.example.appointmentmanager.dto;

import com.example.appointmentmanager.validators.PhoneValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResponsableAddRequest(
        @NotBlank(message = "La référence du responsable est obligatoire")
        String ref,

        @NotBlank(message = "L'adresse email du responsable est obligatoire")
        @Email(message = "Le format de l'adresse email du responsable est incorrect")
        String email,

        @PhoneValidator
        Integer telephone,

        @NotBlank(message = "Le nom du responsable est obligatoire")
        String nom,

        String prenom,

        @NotBlank(message = "La référence du service du responsable est obligatoire")
        String refService
) {
}
