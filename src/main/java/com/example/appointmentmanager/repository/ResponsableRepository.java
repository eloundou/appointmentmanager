package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsableRepository extends JpaRepository<Responsable, Long> {

    Optional<Responsable> findByRef(String ref);

}
