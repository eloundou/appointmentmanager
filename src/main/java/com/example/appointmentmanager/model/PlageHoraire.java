package com.example.appointmentmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "plage_horaires", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"debut", "fin"})
})
@Getter
@Setter
public class PlageHoraire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "debut", nullable = false)
    private LocalTime debut;

    @Column(name = "fin", nullable = false)
    private LocalTime fin;

    @Column(nullable = false)
    private String libelle;

}
