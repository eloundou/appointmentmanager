package com.example.appointmentmanager.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleResourceNotFound(ApplicationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "La validation de l'entité a échouée");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

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

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, userFriendlyMessage);
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

}
