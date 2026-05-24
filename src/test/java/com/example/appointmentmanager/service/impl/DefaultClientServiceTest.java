package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.ClientAddRequest;
import com.example.appointmentmanager.dto.ClientUpdateRequest;
import com.example.appointmentmanager.exceptions.ApplicationException;
import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Client;
import com.example.appointmentmanager.repository.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private DefaultClientService service;

    @Test
    @DisplayName("Should create client")
    void shouldCreateClient() {

        // Arrange

        ClientAddRequest request = new ClientAddRequest(
                "CLI-001", "john@example.com", 677000000, "Doe", "John");

        when(clientRepository.findByRef("CLI-001")).thenReturn(Optional.empty());

        when(clientRepository.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act

        Client result = service.create(request);

        // Assert

        assertThat(result.getRef()).isEqualTo("CLI-001");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getTelephone()).isEqualTo(677000000);
        assertThat(result.getNom()).isEqualTo("Doe");
        assertThat(result.getPrenom()).isEqualTo("John");

        verify(clientRepository).findByRef("CLI-001");
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Should reject duplicate client reference")
    void shouldRejectDuplicateClientReference() {

        // Arrange

        Client existing = new Client();
        existing.setRef("CLI-001");

        ClientAddRequest request = new ClientAddRequest(
                "CLI-001", "john@example.com", 677000000, "Doe", "John");

        when(clientRepository.findByRef("CLI-001")).thenReturn(Optional.of(existing));

        // Act + Assert
        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Un client avec cette référence a déjà été enregistré");

        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Should update client")
    void shouldUpdateClient() {

        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setRef("CLI-001");
        client.setNom("Old");
        client.setPrenom("Name");
        client.setEmail("old@example.com");
        client.setTelephone(600000000);

        ClientUpdateRequest request = new ClientUpdateRequest(
                "new@example.com", 699999999, "Doe", "John");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act

        Client result = service.update(1L, request);

        // Assert

        assertThat(result.getEmail()).isEqualTo("new@example.com");
        assertThat(result.getTelephone()).isEqualTo(699999999);
        assertThat(result.getNom()).isEqualTo("Doe");
        assertThat(result.getPrenom()).isEqualTo("John");
    }

    @Test
    @DisplayName("Should find client by id")
    void shouldFindClientById() {

        // Arrange

        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Act

        Client result = service.findById(1L);

        // Assert

        assertThat(result).isEqualTo(client);
    }

    @Test
    @DisplayName("Should throw when client id does not exist")
    void shouldThrowWhenClientIdDoesNotExist() {

        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Impossible de trouver le client");
    }

    @Test
    @DisplayName("Should find client by reference")
    void shouldFindClientByReference() {

        // Arrange

        Client client = new Client();
        client.setRef("CLI-001");

        when(clientRepository.findByRef("CLI-001")).thenReturn(Optional.of(client));

        // Act

        Client result = service.findByReference("CLI-001");

        // Assert

        assertThat(result).isEqualTo(client);
    }

    @Test
    @DisplayName("Should throw when client reference does not exist")
    void shouldThrowWhenClientReferenceDoesNotExist() {

        // Arrange

        when(clientRepository.findByRef("CLI-001")).thenReturn(Optional.empty());

        // Act + Assert

        assertThatThrownBy(() -> service.findByReference("CLI-001"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Impossible de trouver le client");
    }

    @Test
    @DisplayName("Should return all clients")
    void shouldReturnAllClients() {

        // Arrange

        Client c1 = new Client();
        Client c2 = new Client();

        when(clientRepository.findAll()).thenReturn(List.of(c1, c2));

        // Act

        List<Client> result = service.findAll();

        // Assert

        assertThat(result).hasSize(2);
    }

}