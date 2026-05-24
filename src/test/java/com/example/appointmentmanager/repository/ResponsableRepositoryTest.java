package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Responsable;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ResponsableRepositoryTest {

    private final ResponsableRepository repository;
    private final DepartementRepository departementRepository;
    private final EntityManager entityManager;

    ResponsableRepositoryTest(ResponsableRepository repository,
                              DepartementRepository departementRepository,
                              EntityManager entityManager) {

        this.repository = repository;
        this.departementRepository = departementRepository;
        this.entityManager = entityManager;
    }

    @Test
    @DisplayName("Should find responsable by ref")
    void shouldFindResponsableByRef() {

        // Arrange
        var ref = "RESP0001";
        var departement = departementRepository.findByRef("SERV-DAF").orElse(null);

        var responsable = new Responsable();
        responsable.setRef(ref);
        responsable.setNom("FOKOU");
        responsable.setPrenom("Daniel");
        responsable.setEmail("fokoudaniel@gmail.com");
        responsable.setTelephone(690215562);
        responsable.setService(departement);

        entityManager.persist(responsable);

        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<Responsable> result = repository.findByRef(ref);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getRef()).isEqualTo(ref);
        assertThat(result.get().getNom()).isEqualTo("FOKOU");
        assertThat(result.get().getPrenom()).isEqualTo("Daniel");
        assertThat(result.get().getService().getId()).isEqualTo(departement.getId());
    }

    @Test
    @DisplayName("Should find responsable by email")
    void shouldFindResponsableByEmail() {

        // Arrange
        var email = "fokoudaniel@gmail.com";
        var departement = departementRepository.findByRef("SERV-DAF").orElse(null);

        var responsable = new Responsable();
        responsable.setRef("RESP0001");
        responsable.setNom("FOKOU");
        responsable.setPrenom("Daniel");
        responsable.setEmail(email);
        responsable.setTelephone(690215562);
        responsable.setService(departement);

        entityManager.persist(responsable);

        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<Responsable> result = repository.findByEmail(email);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Should find responsable by service")
    void shouldFindResponsableByService() {

        // Arrange
        var email = "fokoudaniel@gmail.com";
        var departement = departementRepository.findByRef("SERV-DAF").orElse(null);

        var responsable = new Responsable();
        responsable.setRef("RESP0001");
        responsable.setNom("FOKOU");
        responsable.setPrenom("Daniel");
        responsable.setEmail(email);
        responsable.setTelephone(690215562);
        responsable.setService(departement);

        entityManager.persist(responsable);

        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<Responsable> result = repository.findByRefService(departement.getRef());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getService().getId()).isEqualTo(departement.getId());
    }

    @Test
    @DisplayName("Should return empty when ref does not exist")
    void shouldReturnEmptyWhenRefDoesNotExist() {

        // Act
        Optional<Responsable> result = repository.findByRef("UNKNOWN");

        // Assert
        assertThat(result).isEmpty();
    }

}