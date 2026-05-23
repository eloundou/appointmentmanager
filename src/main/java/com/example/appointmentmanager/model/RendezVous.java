package com.example.appointmentmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ref;

    @Column(nullable = false)
    private String motif;

    @Column(nullable = false)
    private LocalDate dateRendezVous;

    @ManyToOne
    @JoinColumn(name = "plage_horaire_id")
    private PlageHoraire plageHoraire;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Departement departement;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Responsable responsable;

}
