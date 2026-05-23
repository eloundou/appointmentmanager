package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
