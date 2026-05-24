package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.model.PlageHoraire;
import com.example.appointmentmanager.service.PlageHoraireService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlageHoraireController.class)
class PlageHoraireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlageHoraireService plageHoraireService;

    @Test
    @DisplayName("Should return all plages horaires")
    void shouldReturnAllPlageHoraires() throws Exception {

        // Arrange

        PlageHoraire p1 = new PlageHoraire();
        p1.setId(1L);
        p1.setDebut(LocalTime.of(8, 0));
        p1.setFin(LocalTime.of(9, 0));
        p1.setLibelle("8h");

        PlageHoraire p2 = new PlageHoraire();
        p2.setId(2L);
        p2.setDebut(LocalTime.of(9, 0));
        p2.setFin(LocalTime.of(10, 0));
        p2.setLibelle("9h");

        when(plageHoraireService.findAll()).thenReturn(List.of(p1, p2));

        // Act + Assert

        mockMvc.perform(get("/plage-horaires/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].debut").value(LocalTime.of(8, 0).format(DateTimeFormatter.ISO_LOCAL_TIME)))
                .andExpect(jsonPath("$[0].fin").value(LocalTime.of(9, 0).format(DateTimeFormatter.ISO_LOCAL_TIME)))
                .andExpect(jsonPath("$[0].libelle").value("8h"))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].debut").value(LocalTime.of(9, 0).format(DateTimeFormatter.ISO_LOCAL_TIME)))
                .andExpect(jsonPath("$[1].fin").value(LocalTime.of(10, 0).format(DateTimeFormatter.ISO_LOCAL_TIME)))
                .andExpect(jsonPath("$[1].libelle").value("9h"));
    }

}