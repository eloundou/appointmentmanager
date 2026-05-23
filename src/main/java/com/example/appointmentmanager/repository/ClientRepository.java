package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
