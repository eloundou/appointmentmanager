package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.PlageHoraire;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlageHoraireRepositoryTest {

    @Autowired
    private PlageHoraireRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should find plage horaire containing time")
    void shouldFindPlageHoraireContainingTime() {

        // Arrange

        // Act
        Optional<PlageHoraire> result = repository.findWithTimeBetweenDebutAndFin(LocalTime.of(8, 20));

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getDebut()).isEqualTo(LocalTime.of(8, 0));
        assertThat(result.get().getFin()).isEqualTo(LocalTime.of(9, 0));

    }

    @Test
    @DisplayName("Should return empty when time is not in registered plage horaire")
    void shouldReturnEmptyWhenTimeIsNotInRegisteredPlageHoraire() {

        // Arrange

        // Act
        Optional<PlageHoraire> result = repository.findWithTimeBetweenDebutAndFin(LocalTime.of(7, 0));

        // Assert
        assertThat(result).isEmpty();
    }

}