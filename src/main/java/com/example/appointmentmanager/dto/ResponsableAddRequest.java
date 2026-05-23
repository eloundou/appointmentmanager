package com.example.appointmentmanager.dto;

public record ResponsableAddRequest(String ref, String email, Integer telephone, String nom, String prenom, String refService) {
}
