package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.dto.ResponsableAddRequest;
import com.example.appointmentmanager.dto.ResponsableUpdateRequest;
import com.example.appointmentmanager.exceptions.ApplicationException;
import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.model.Responsable;
import com.example.appointmentmanager.repository.ResponsableRepository;
import com.example.appointmentmanager.service.DepartementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultResponsableServiceTest {

    @Mock
    private ResponsableRepository responsableRepository;

    @Mock
    private DepartementService departementService;

    @InjectMocks
    private DefaultResponsableService service;

    // ---------------- CREATE ----------------

    @Test
    @DisplayName("Should create responsable successfully")
    void shouldCreateResponsable() {

        ResponsableAddRequest request = new ResponsableAddRequest(
                "REF-1", "email@test.com", 677000000, "Doe", "John", "DEP-1");

        Departement departement = new Departement();
        departement.setRef("DEP-1");

        when(responsableRepository.findByRef("REF-1")).thenReturn(Optional.empty());

        when(responsableRepository.findByRefService("DEP-1")).thenReturn(Optional.empty());

        when(responsableRepository.findByEmail("email@test.com")).thenReturn(Optional.empty());

        when(departementService.findByReference("DEP-1")).thenReturn(departement);

        when(responsableRepository.save(any(Responsable.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        Responsable result = service.create(request);

        assertThat(result.getRef()).isEqualTo("REF-1");
        assertThat(result.getEmail()).isEqualTo("email@test.com");

        verify(responsableRepository).save(any(Responsable.class));
    }

    @Test
    @DisplayName("Should throw when ref already exists")
    void shouldThrowWhenRefExists() {

        Responsable existing = new Responsable();

        when(responsableRepository.findByRef("REF-1")).thenReturn(Optional.of(existing));

        ResponsableAddRequest request = mock(ResponsableAddRequest.class);
        when(request.ref()).thenReturn("REF-1");

        assertThatThrownBy(() -> service.create(request)).isInstanceOf(ApplicationException.class);

        verify(responsableRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw when email already exists")
    void shouldThrowWhenEmailExists() {

        Responsable existing = new Responsable();

        ResponsableAddRequest request = mock(ResponsableAddRequest.class);
        when(request.ref()).thenReturn("REF-1");
        when(request.refService()).thenReturn("DEP-1");
        when(request.email()).thenReturn("email@test.com");

        when(responsableRepository.findByRef("REF-1")).thenReturn(Optional.empty());

        when(responsableRepository.findByRefService("DEP-1")).thenReturn(Optional.empty());

        when(responsableRepository.findByEmail("email@test.com")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> service.create(request)).isInstanceOf(ApplicationException.class);

        verify(responsableRepository, never()).save(any());
    }

    // ---------------- UPDATE ----------------

    @Test
    @DisplayName("Should update responsable successfully")
    void shouldUpdateResponsable() {

        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setEmail("old@test.com");

        ResponsableUpdateRequest request = mock(ResponsableUpdateRequest.class);
        when(request.email()).thenReturn("new@test.com");
        when(request.telephone()).thenReturn(677000000);
        when(request.nom()).thenReturn("Doe");
        when(request.prenom()).thenReturn("John");

        when(responsableRepository.findById(1L)).thenReturn(Optional.of(responsable));

        when(responsableRepository.findByEmail("new@test.com")).thenReturn(Optional.empty());

        when(responsableRepository.save(any(Responsable.class))).thenAnswer(i -> i.getArgument(0));

        Responsable result = service.update(1L, request);

        assertThat(result.getEmail()).isEqualTo("new@test.com");

        verify(responsableRepository).save(responsable);
    }

    @Test
    @DisplayName("Should throw when updating with existing email")
    void shouldThrowWhenUpdatingWithExistingEmail() {

        Responsable existing = new Responsable();
        existing.setId(2L);

        Responsable responsable = new Responsable();
        responsable.setId(1L);

        ResponsableUpdateRequest request = mock(ResponsableUpdateRequest.class);
        when(request.email()).thenReturn("email@test.com");

        when(responsableRepository.findById(1L)).thenReturn(Optional.of(responsable));

        when(responsableRepository.findByEmail("email@test.com")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> service.update(1L, request)).isInstanceOf(ApplicationException.class);

        verify(responsableRepository, never()).save(any());
    }

    // ---------------- FIND ----------------

    @Test
    @DisplayName("Should find by id")
    void shouldFindById() {

        Responsable responsable = new Responsable();
        responsable.setId(1L);

        when(responsableRepository.findById(1L)).thenReturn(Optional.of(responsable));

        Responsable result = service.findById(1L);

        assertThat(result).isEqualTo(responsable);
    }

    @Test
    @DisplayName("Should throw when not found by id")
    void shouldThrowWhenNotFoundById() {

        when(responsableRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Should find all responsables")
    void shouldFindAll() {


        when(responsableRepository.findAll()).thenReturn(List.of(new Responsable(), new Responsable()));

        List<Responsable> result = service.findAll();

        assertThat(result).hasSize(2);
    }

}