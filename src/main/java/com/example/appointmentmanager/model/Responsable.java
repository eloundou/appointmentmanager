package com.example.appointmentmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "responsables",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_responsable_ref", columnNames = {"ref"}),
                @UniqueConstraint(name = "uk_responsable_email", columnNames = {"email"}),
                @UniqueConstraint(name = "uk_responsable_service_id", columnNames = {"service_id"})
        }
)
@Getter
@Setter
public class Responsable extends Utilisateur {

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Departement service;


}
