package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.repository.DepartementRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDepartementServiceTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DefaultDepartementService service;

    @Test
    @DisplayName("Should find departement by reference")
    void shouldFindDepartementByReference() {

        // Arrange

        Departement departement = new Departement();
        departement.setRef("CARDIO");
        departement.setNom("Cardiology");

        when(departementRepository.findByRef("CARDIO")).thenReturn(Optional.of(departement));

        // Act

        Departement result = service.findByReference("CARDIO");

        // Assert

        assertThat(result).isEqualTo(departement);
        assertThat(result.getRef()).isEqualTo("CARDIO");
        assertThat(result.getNom()).isEqualTo("Cardiology");

        verify(departementRepository).findByRef("CARDIO");
    }

    @Test
    @DisplayName("Should throw when departement reference does not exist")
    void shouldThrowWhenDepartementReferenceDoesNotExist() {

        // Arrange

        when(departementRepository.findByRef("UNKNOWN")).thenReturn(Optional.empty());

        // Act + Assert

        assertThatThrownBy(() -> service.findByReference("UNKNOWN"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Impossible de trouver le service avec la référence");

        verify(departementRepository).findByRef("UNKNOWN");
    }

    @Test
    @DisplayName("Should return all departements")
    void shouldReturnAllDepartements() {

        // Arrange

        Departement d1 = new Departement();
        d1.setRef("CARDIO");

        Departement d2 = new Departement();
        d2.setRef("RADIO");

        when(departementRepository.findAll()).thenReturn(List.of(d1, d2));

        // Act

        List<Departement> result = service.findAll();

        // Assert

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Departement::getRef)
                .containsExactly("CARDIO", "RADIO");

        verify(departementRepository).findAll();
    }

}