package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.model.Client;
import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.model.RendezVous;
import com.example.appointmentmanager.model.Responsable;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RendezVousRepositoryTest {

    private final RendezVousRepository repository;
    private final DepartementRepository departementRepository;
    private final PlageHoraireRepository plageHoraireRepository;
    private final EntityManager entityManager;

    RendezVousRepositoryTest(RendezVousRepository repository,
                             DepartementRepository departementRepository,
                             PlageHoraireRepository plageHoraireRepository,
                             EntityManager entityManager) {

        this.repository = repository;
        this.departementRepository = departementRepository;
        this.plageHoraireRepository = plageHoraireRepository;
        this.entityManager = entityManager;
    }

    @Test
    @DisplayName("Should find rendez-vous by departement plage and date")
    void shouldFindByDepartementPlageAndDate() {

        // Arrange
        var departement = departementRepository.findByRef("SERV-DAF").orElse(null);
        var plageHoraire = plageHoraireRepository.findWithTimeBetweenDebutAndFin(
                LocalTime.of(8, 0)).orElse(null);
        var rendezVousDate = LocalDate.of(2026, 5, 24);

        var client = newClient();
        var responsable = newResponsable(departement);

        var rendezVous = new RendezVous();
        rendezVous.setRef("RDV001");
        rendezVous.setMotif("Consultation de certains points financiers");
        rendezVous.setClient(client);
        rendezVous.setResponsable(responsable);
        rendezVous.setDepartement(departement);
        rendezVous.setPlageHoraire(plageHoraire);
        rendezVous.setDateRendezVous(rendezVousDate);

        entityManager.persist(rendezVous);

        entityManager.flush();
        entityManager.clear();

        // Act

        var result = repository.findByServiceAndPlageAndDate(departement, plageHoraire, rendezVousDate);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getDepartement().getId()).isEqualTo(departement.getId());
        assertThat(result.get().getPlageHoraire().getDebut()).isEqualTo(LocalTime.of(8, 0));
        assertThat(result.get().getDateRendezVous()).isEqualTo(rendezVousDate);
    }

    @Test
    @DisplayName("Should return empty when date does not match")
    void shouldReturnEmptyWhenDateDoesNotMatch() {

        // Arrange

        var departement = departementRepository.findByRef("SERV-DAF").orElse(null);
        var plageHoraire = plageHoraireRepository.findWithTimeBetweenDebutAndFin(
                LocalTime.of(10, 0)).orElse(null);

        var client = newClient();
        var responsable = newResponsable(departement);

        var rendezVous = new RendezVous();
        rendezVous.setRef("RDV001");
        rendezVous.setMotif("Consultation de certains points financiers");
        rendezVous.setClient(client);
        rendezVous.setResponsable(responsable);
        rendezVous.setDepartement(departement);
        rendezVous.setPlageHoraire(plageHoraire);
        rendezVous.setDateRendezVous(LocalDate.of(2026, 5, 24));

        entityManager.persist(rendezVous);

        entityManager.flush();
        entityManager.clear();

        // Act
        var result = repository.findByServiceAndPlageAndDate(
                departement, plageHoraire, LocalDate.of(2026, 5, 25));

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when departement does not match")
    void shouldReturnEmptyWhenDepartementDoesNotMatch() {

        // Arrange

        var departement1 = departementRepository.findByRef("SERV-DAF").orElse(null);
        var departement2 = departementRepository.findByRef("SERV-RH").orElse(null);
        var plageHoraire = plageHoraireRepository.findWithTimeBetweenDebutAndFin(
                LocalTime.of(14, 0)).orElse(null);

        var client = newClient();
        var responsable = newResponsable(departement1);

        LocalDate date = LocalDate.of(2026, 5, 24);

        var rendezVous = new RendezVous();
        rendezVous.setRef("RDV001");
        rendezVous.setMotif("Consultation de certains points financiers");
        rendezVous.setClient(client);
        rendezVous.setResponsable(responsable);
        rendezVous.setDepartement(departement1);
        rendezVous.setPlageHoraire(plageHoraire);
        rendezVous.setDateRendezVous(LocalDate.of(2026, 5, 24));

        entityManager.persist(rendezVous);

        entityManager.flush();
        entityManager.clear();

        // Act
        var result = repository.findByServiceAndPlageAndDate(departement2, plageHoraire, date);

        // Assert
        assertThat(result).isEmpty();
    }

    private Responsable newResponsable(Departement departement) {
        var responsable = new Responsable();
        responsable.setRef("RESP0001");
        responsable.setNom("FOKOU");
        responsable.setPrenom("Daniel");
        responsable.setEmail("fokoudaniel@gmail.com");
        responsable.setTelephone(690215562);
        responsable.setService(departement);

        entityManager.persist(responsable);
        return responsable;
    }

    private Client newClient() {
        var client = new Client();
        client.setRef("CLT0001");
        client.setNom("FINTAMKO");
        client.setPrenom("Junior");
        client.setEmail("fitamkojunior@gmail.com");
        client.setTelephone(690215563);

        entityManager.persist(client);
        return client;
    }

}