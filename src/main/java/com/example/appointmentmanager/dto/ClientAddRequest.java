package com.example.appointmentmanager.dto;

import com.example.appointmentmanager.validators.PhoneValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientAddRequest(
        @NotBlank(message = "La référence du client est obligatoire")
        String ref,

        @NotBlank(message = "L'adresse email du client est obligatoire")
        @Email(message = "Le format de l'adresse email du client est incorrect")
        String email,

        @PhoneValidator
        Integer telephone,

        @NotBlank(message = "Le nom du client est obligatoire")
        String nom,

        String prenom) {
}
