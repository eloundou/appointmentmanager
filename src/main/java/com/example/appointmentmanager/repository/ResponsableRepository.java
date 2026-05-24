package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Responsable;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResponsableRepository extends JpaRepository<Responsable, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Responsable r WHERE r.ref = :ref")
    Optional<Responsable> findByRef(String ref);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Responsable r WHERE r.service.ref = :ref")
    Optional<Responsable> findByRefService(String ref);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Responsable r WHERE r.email = :email")
    Optional<Responsable> findByEmail(String email);

}
