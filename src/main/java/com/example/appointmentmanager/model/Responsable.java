package com.example.appointmentmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "responsables")
@Getter
@Setter
public class Responsable extends Utilisateur {

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Departement service;


}
