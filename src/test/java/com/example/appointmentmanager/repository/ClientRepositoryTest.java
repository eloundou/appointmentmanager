package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Client;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should find client by ref")
    void shouldFindClientByRef() {

        // Arrange
        Client client = new Client();
        client.setRef("CLI-001");
        client.setEmail("mvogoeloundou@gmail.com");
        client.setTelephone(690215563);
        client.setNom("Eloundou Mvogo");
        client.setPrenom("Victor");

        entityManager.persist(client);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<Client> result = repository.findByRef("CLI-001");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getRef()).isEqualTo("CLI-001");
    }

    @Test
    @DisplayName("Should find client by email")
    void shouldFindClientByEmail() {

        // Arrange
        Client client = new Client();
        client.setRef("CLI-001");
        client.setEmail("mvogoeloundou@gmail.com");
        client.setTelephone(690215563);
        client.setNom("Eloundou Mvogo");
        client.setPrenom("Victor");

        entityManager.persist(client);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<Client> result = repository.findByEmail("mvogoeloundou@gmail.com");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("mvogoeloundou@gmail.com");
    }

    @Test
    @DisplayName("Should return empty when ref does not exist")
    void shouldReturnEmptyWhenRefDoesNotExist() {

        // Act
        Optional<Client> result = repository.findByRef("UNKNOWN");

        // Assert
        assertThat(result).isEmpty();
    }

}