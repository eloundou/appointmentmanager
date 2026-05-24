package com.example.appointmentmanager.service.impl;

import com.example.appointmentmanager.exceptions.ResourceNotFoundException;
import com.example.appointmentmanager.model.PlageHoraire;
import com.example.appointmentmanager.repository.PlageHoraireRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultPlageHoraireServiceTest {

    @Mock
    private PlageHoraireRepository plageHoraireRepository;

    @InjectMocks
    private DefaultPlageHoraireService service;

    @Test
    @DisplayName("Should return all plage horaires")
    void shouldReturnAllPlageHoraires() {

        // Arrange
        PlageHoraire p1 = new PlageHoraire();
        PlageHoraire p2 = new PlageHoraire();

        when(plageHoraireRepository.findAll()).thenReturn(List.of(p1, p2));

        // Act
        List<PlageHoraire> result = service.findAll();

        // Assert
        assertThat(result).hasSize(2);

        verify(plageHoraireRepository).findAll();
    }

    @Test
    @DisplayName("Should return plage horaire when time matches")
    void shouldReturnPlageHoraireWhenTimeMatches() {

        // Arrange
        LocalTime time = LocalTime.of(10, 0);

        PlageHoraire plage = new PlageHoraire();
        plage.setDebut(LocalTime.of(10, 0));
        plage.setFin(LocalTime.of(11, 0));

        when(plageHoraireRepository.findWithTimeBetweenDebutAndFin(time)).thenReturn(Optional.of(plage));

        // Act
        PlageHoraire result = service.findWithTimeBetweenDebutAndFin(time);

        // Assert
        assertThat(result.getDebut()).isEqualTo(LocalTime.of(10, 0));
        assertThat(result.getFin()).isEqualTo(LocalTime.of(11, 0));

        verify(plageHoraireRepository).findWithTimeBetweenDebutAndFin(time);
    }

    @Test
    @DisplayName("Should throw exception when no plage horaire found")
    void shouldThrowExceptionWhenNoPlageHoraireFound() {

        // Arrange
        LocalTime time = LocalTime.of(10, 0);

        when(plageHoraireRepository.findWithTimeBetweenDebutAndFin(time)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> service.findWithTimeBetweenDebutAndFin(time))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Aucune plage horaire trouvée");

        verify(plageHoraireRepository).findWithTimeBetweenDebutAndFin(time);
    }

}