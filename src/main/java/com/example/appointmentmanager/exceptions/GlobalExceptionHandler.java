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

        // Match against the specific constraint names defined in Flyway/Entity
        if (rootMessage.contains("uk_responsables_ref")) {
            userFriendlyMessage = "Un responsable avec cette référence a déjà été enregistré";
        } else if (rootMessage.contains("uk_responsables_email")) {
            userFriendlyMessage = "Un responsable avec cette adresse email a déjà été enregistré";
        } else if (rootMessage.contains("uk_responsables_service_id") || rootMessage.contains("uk_responsables_service")) {
            userFriendlyMessage = "Un responsable avec ce service a déjà été enregistré";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", userFriendlyMessage));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, String>> handleApplicationException(ApplicationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

}
