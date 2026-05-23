package com.example.appointmentmanager.dto;

import jakarta.validation.constraints.Email;

public record ResponsableUpdateRequest(
        @Email String email,
        Integer telephone,
        String nom,
        String prenom) {
}
