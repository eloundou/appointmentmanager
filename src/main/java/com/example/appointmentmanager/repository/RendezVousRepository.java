package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Client;
import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.model.PlageHoraire;
import com.example.appointmentmanager.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    @Query("""
            SELECT r FROM RendezVous r
            WHERE r.departement = :departement AND r.plageHoraire = :plageHoraire AND r.dateRendezVous = :dateRendezVous
            """)
    Optional<RendezVous> findByServiceAndPlageAndDate(Departement departement, PlageHoraire plageHoraire,
                                                      LocalDate dateRendezVous);

    @Query("""
            SELECT r FROM RendezVous r
            WHERE r.client = :client AND r.plageHoraire = :plageHoraire AND r.dateRendezVous = :dateRendezVous
            """)
    Optional<RendezVous> findByClientAndPlageAndDate(Client client, PlageHoraire plageHoraire, LocalDate dateRendezVous);

    @Query("SELECT r FROM RendezVous r WHERE r.ref = :ref")
    Optional<RendezVous> findByRef(String ref);

}