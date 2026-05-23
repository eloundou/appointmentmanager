package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
}
