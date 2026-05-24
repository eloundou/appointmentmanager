package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.RendezVousCreateRequest;
import com.example.appointmentmanager.exceptions.ApplicationException;
import com.example.appointmentmanager.model.*;
import com.example.appointmentmanager.repository.RendezVousRepository;
import com.example.appointmentmanager.service.ClientService;
import com.example.appointmentmanager.service.DepartementService;
import com.example.appointmentmanager.service.PlageHoraireService;
import com.example.appointmentmanager.service.ResponsableService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRendezVousServiceTest {

    @Mock
    private RendezVousRepository repository;

    @Mock
    private DepartementService departementService;

    @Mock
    private ResponsableService responsableService;

    @Mock
    private ClientService clientService;

    @Mock
    private PlageHoraireService plageHoraireService;

    @InjectMocks
    private DefaultRendezVousService service;

    @Test
    @DisplayName("Should create rendez-vous successfully")
    void shouldCreateRendezVousSuccessfully() {

        // Arrange

        LocalDateTime dateTime = LocalDateTime.now().plusDays(5);

        RendezVousCreateRequest request = new RendezVousCreateRequest("CLT-001",
                "RDV-001", "DEP-1", "RESP-1", dateTime, "Consultation");

        Departement departement = new Departement();
        departement.setId(1L);

        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setService(departement);

        Client client = new Client();
        client.setId(1L);

        PlageHoraire plageHoraire = new PlageHoraire();
        plageHoraire.setDebut(LocalTime.of(8, 0));
        plageHoraire.setFin(LocalTime.of(12, 0));

        when(repository.findByRef("RDV-001")).thenReturn(Optional.empty());
        when(plageHoraireService.findWithTimeBetweenDebutAndFin(any())).thenReturn(plageHoraire);
        when(responsableService.findByReference("RESP-1")).thenReturn(responsable);
        when(departementService.findByReference("DEP-1")).thenReturn(departement);
        when(clientService.findByReference("CLT-001")).thenReturn(client);

        when(repository.findByServiceAndPlageAndDate(any(), any(), any())).thenReturn(Optional.empty());

        when(repository.findByClientAndPlageAndDate(any(), any(), any())).thenReturn(Optional.empty());

        when(repository.save(any(RendezVous.class))).thenAnswer(i -> i.getArgument(0));

        // Act

        RendezVous result = service.create(request);

        // Assert

        assertThat(result.getRef()).isEqualTo("RDV-001");
        assertThat(result.getMotif()).isEqualTo("Consultation");
        assertThat(result.getClient()).isEqualTo(client);
        assertThat(result.getResponsable()).isEqualTo(responsable);
        assertThat(result.getDepartement()).isEqualTo(departement);

        verify(repository).save(any(RendezVous.class));
    }

    @Test
    @DisplayName("Should reject rendez-vous less than 48h in advance")
    void shouldRejectRendezVousLessThan48HoursInAdvance() {

        // Arrange

        RendezVousCreateRequest request = new RendezVousCreateRequest("CLT-001",
                "RDV-001", "DEP-1", "RESP-1", LocalDateTime.now().plusHours(24), "Consultation");

        // Act + Assert

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("48h");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should reject duplicate rendez-vous reference")
    void shouldRejectDuplicateReference() {

        // Arrange

        RendezVous existing = new RendezVous();

        RendezVousCreateRequest request = new RendezVousCreateRequest("CLT-001",
                "RDV-001", "DEP-1", "RESP-1", LocalDateTime.now().plusDays(5), "Consultation");


        when(repository.findByRef("RDV-001")).thenReturn(Optional.of(existing));

        // Act + Assert

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("référence");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should reject when responsable service does not match")
    void shouldRejectWhenResponsableServiceDoesNotMatch() {

        // Arrange

        LocalDateTime dateTime = LocalDateTime.now().plusDays(5);

        RendezVousCreateRequest request = new RendezVousCreateRequest("CLT-001",
                "RDV-001", "DEP-1", "RESP-1", dateTime, "Consultation");

        Departement responsableDepartement = new Departement();
        responsableDepartement.setId(1L);

        Departement requestedDepartement = new Departement();
        requestedDepartement.setId(2L);

        Responsable responsable = new Responsable();
        responsable.setService(responsableDepartement);

        Client client = new Client();

        PlageHoraire plageHoraire = new PlageHoraire();

        when(repository.findByRef(any())).thenReturn(Optional.empty());
        when(plageHoraireService.findWithTimeBetweenDebutAndFin(any())).thenReturn(plageHoraire);
        when(responsableService.findByReference(any())).thenReturn(responsable);
        when(departementService.findByReference(any())).thenReturn(requestedDepartement);
        when(clientService.findByReference(any())).thenReturn(client);

        // Act + Assert

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("ne correspond pas");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should reject when slot already reserved for service")
    void shouldRejectWhenSlotAlreadyReservedForService() {

        // Arrange

        LocalDateTime dateTime = LocalDateTime.now().plusDays(5);

        RendezVousCreateRequest request = new RendezVousCreateRequest("CLT-001",
                "RDV-001", "DEP-1", "RESP-1", dateTime, "Consultation");


        Departement departement = new Departement();
        departement.setId(1L);

        Responsable responsable = new Responsable();
        responsable.setService(departement);

        Client client = new Client();

        PlageHoraire plageHoraire = new PlageHoraire();

        when(repository.findByRef(any())).thenReturn(Optional.empty());
        when(plageHoraireService.findWithTimeBetweenDebutAndFin(any())).thenReturn(plageHoraire);
        when(responsableService.findByReference(any())).thenReturn(responsable);
        when(departementService.findByReference(any())).thenReturn(departement);
        when(clientService.findByReference(any())).thenReturn(client);
        when(repository.findByServiceAndPlageAndDate(any(), any(), any())).thenReturn(Optional.of(new RendezVous()));

        // Act + Assert

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("déjà été enregistré");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should reject when client already has rendez-vous at same slot")
    void shouldRejectWhenClientAlreadyHasRendezVousAtSameSlot() {

        // Arrange

        LocalDateTime dateTime = LocalDateTime.now().plusDays(5);

        RendezVousCreateRequest request = new RendezVousCreateRequest("CLT-001",
                "RDV-001", "DEP-1", "RESP-1", dateTime, "Consultation");

        Departement departement = new Departement();
        departement.setId(1L);

        Responsable responsable = new Responsable();
        responsable.setService(departement);

        Client client = new Client();

        PlageHoraire plageHoraire = new PlageHoraire();

        when(repository.findByRef(any())).thenReturn(Optional.empty());
        when(plageHoraireService.findWithTimeBetweenDebutAndFin(any())).thenReturn(plageHoraire);
        when(responsableService.findByReference(any())).thenReturn(responsable);
        when(departementService.findByReference(any())).thenReturn(departement);
        when(clientService.findByReference(any())).thenReturn(client);
        when(repository.findByServiceAndPlageAndDate(any(), any(), any())).thenReturn(Optional.empty());
        when(repository.findByClientAndPlageAndDate(any(), any(), any())).thenReturn(Optional.of(new RendezVous()));

        // Act + Assert

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("client spécifié");

        verify(repository, never()).save(any());
    }

}