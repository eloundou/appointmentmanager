package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.ref = :ref")
    Optional<Client> findByRef(String ref);

    @Query("SELECT c FROM Client c WHERE c.email = :email")
    Optional<Client> findByEmail(String email);

}
