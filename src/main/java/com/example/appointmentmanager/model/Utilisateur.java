package com.example.appointmentmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ref;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer telephone;

    @Column(nullable = false)
    private String nom;
    private String prenom;

}
