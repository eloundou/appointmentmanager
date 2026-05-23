package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.PlageHoraire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.Optional;

public interface PlageHoraireRepository extends JpaRepository<PlageHoraire, Long> {

    @Query("""
            SELECT p FROM PlageHoraire p
            WHERE p.debut >= :time AND :time < p.fin
            """)
    Optional<PlageHoraire> findWithTimeBetweenDebutAndFin(LocalTime time);

}
