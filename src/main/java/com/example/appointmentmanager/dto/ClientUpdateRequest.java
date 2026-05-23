package com.example.appointmentmanager.dto;

import com.example.appointmentmanager.validators.PhoneValidator;
import jakarta.validation.constraints.Email;

public record ClientUpdateRequest(
        @Email String email,
        @PhoneValidator Integer telephone,
        String nom,
        String prenom) {
}
