package com.example.appointmentmanager.dto;

public record ClientUpdateRequest(String email, Integer telephone, String nom, String prenom) {
}
