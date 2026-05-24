package com.example.appointmentmanager.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        // Extract the root cause message containing the SQL constraint name
        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";

        String userFriendlyMessage = "Une erreur de contrainte de données est survenue.";

        // Match against the specific constraint names defined in Entity

        if (rootMessage.contains("uk_clients_ref")) {
            userFriendlyMessage = "Un client avec cette référence a déjà été enregistré";
        } else if (rootMessage.contains("uk_clients_email")) {
            userFriendlyMessage = "Un client avec cette adresse email a déjà été enregistré";
        } else if (rootMessage.contains("uk_responsable_ref")) {
            userFriendlyMessage = "Un responsable avec cette référence a déjà été enregistré";
        } else if (rootMessage.contains("uk_responsable_email")) {
            userFriendlyMessage = "Un responsable avec cette adresse email a déjà été enregistré";
        } else if (rootMessage.contains("uk_responsable_service_id") || rootMessage.contains("uk_responsable_service")) {
            userFriendlyMessage = "Un responsable avec ce service a déjà été enregistré";
        } else if (rootMessage.contains("uk_appointment_dept_plage_date")) {
            userFriendlyMessage = "Un rendez-vous a déjà été enregistré pour le service spécifié à cette date et à cette plage.";
        } else if (rootMessage.contains("uk_appointment_clt_plage_date")) {
            userFriendlyMessage = "Le client spécifié est a déjà un rendez-vous enregistré à cette date à cette plage.";
        } else if (rootMessage.contains("uk_appointment_ref")) {
            userFriendlyMessage = "Un rendez-vous avec cette référence a déjà été enregistré.";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", userFriendlyMessage));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, String>> handleApplicationException(ApplicationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

}
