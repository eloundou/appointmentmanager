package com.example.appointmentmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_clients_ref", columnNames = {"ref"}),
                @UniqueConstraint(name = "uk_clients_email", columnNames = {"email"})
        }
)
@Getter
@Setter
public class Client extends Utilisateur {
}
