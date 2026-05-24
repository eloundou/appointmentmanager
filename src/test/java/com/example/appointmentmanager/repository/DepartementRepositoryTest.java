package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Departement;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartementRepositoryTest {

    @Autowired
    private DepartementRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should find departement by ref")
    void shouldFindDepartementByRef() {

        // Arrange
        String ref = UUID.randomUUID().toString();

        Departement departement = new Departement();
        departement.setRef(ref);
        departement.setNom("Cardiology");

        entityManager.persist(departement);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<Departement> result = repository.findByRef(ref);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getRef()).isEqualTo(ref);
        assertThat(result.get().getNom()).isEqualTo("Cardiology");
    }

    @Test
    @DisplayName("Should return empty when ref does not exist")
    void shouldReturnEmptyWhenRefDoesNotExist() {

        // Act
        Optional<Departement> result = repository.findByRef("UNKNOWN");

        // Assert
        assertThat(result).isEmpty();
    }

}