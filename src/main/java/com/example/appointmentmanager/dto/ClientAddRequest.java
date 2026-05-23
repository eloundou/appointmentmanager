package com.example.appointmentmanager.dto;

public record ClientAddRequest(String ref, String email, Integer telephone, String nom, String prenom) {
}
