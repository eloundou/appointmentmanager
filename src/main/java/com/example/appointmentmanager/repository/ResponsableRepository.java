package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResponsableRepository extends JpaRepository<Responsable, Long> {

    @Query("SELECT r FROM Responsable r WHERE r.ref = :ref")
    Optional<Responsable> findByRef(String ref);

    @Query("SELECT r FROM Responsable r WHERE r.service.ref = :ref")
    Optional<Responsable> findByRefService(String ref);

    @Query("SELECT r FROM Responsable r WHERE r.email = :email")
    Optional<Responsable> findByEmail(String email);

}
