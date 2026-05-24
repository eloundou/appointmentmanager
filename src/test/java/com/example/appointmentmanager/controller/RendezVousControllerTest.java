package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.dto.RendezVousCreateRequest;
import com.example.appointmentmanager.model.*;
import com.example.appointmentmanager.service.RendezVousService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RendezVousController.class)
class RendezVousControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RendezVousService rendezVousService;

    @Test
    @DisplayName("Should create rendez-vous successfully")
    void shouldCreateRendezVousSuccessfully() throws Exception {

        // Arrange

        Client client = new Client();
        client.setId(1L);
        client.setRef("CLI-001");

        Departement departement = new Departement();
        departement.setId(1L);
        departement.setRef("DEP-001");

        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setRef("RESP-001");

        PlageHoraire plageHoraire = new PlageHoraire();
        plageHoraire.setId(1L);
        plageHoraire.setDebut(LocalTime.of(8, 0));
        plageHoraire.setFin(LocalTime.of(12, 0));

        RendezVous rendezVous = new RendezVous();
        rendezVous.setId(1L);
        rendezVous.setRef("RDV-001");
        rendezVous.setMotif("Consultation");
        rendezVous.setDateRendezVous(LocalDate.of(2026, 6, 1));
        rendezVous.setClient(client);
        rendezVous.setDepartement(departement);
        rendezVous.setResponsable(responsable);
        rendezVous.setPlageHoraire(plageHoraire);

        RendezVousCreateRequest request = new RendezVousCreateRequest(
                "CLI-001", "RDV-001", "DEP-001", "RESP-001",
                java.time.LocalDateTime.of(2026, 6, 1, 9, 0, 0),
                "Consultation");

        when(rendezVousService.create(any(RendezVousCreateRequest.class))).thenReturn(rendezVous);

        // Act + Assert

        mockMvc.perform(post("/rendezvous")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.ref").value("RDV-001"))
                .andExpect(jsonPath("$.motif").value("Consultation"))

                .andExpect(jsonPath("$.client.ref").value("CLI-001"))
                .andExpect(jsonPath("$.departement.ref").value("DEP-001"))
                .andExpect(jsonPath("$.responsable.ref").value("RESP-001"));
    }

    @Test
    @DisplayName("Should return bad request when request is invalid")
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {

        // Arrange

        String invalidRequest = """
                {
                    "refClient": "",
                    "refRDV": "",
                    "refService": "",
                    "refResponsable": "",
                    "dateRDV": null,
                    "motifRDV": ""
                }
                """;

        // Act + Assert

        mockMvc.perform(post("/rendezvous")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when date format is invalid")
    void shouldReturnBadRequestWhenDateFormatIsInvalid() throws Exception {

        // Arrange

        String invalidRequest = """
                {
                    "refClient": "CLI-001",
                    "refRDV": "RDV-001",
                    "refService": "DEP-001",
                    "refResponsable": "RESP-001",
                    "dateRDV": "01-06-2026",
                    "motifRDV": "Consultation"
                }
                """;

        // Act + Assert

        mockMvc.perform(post("/rendezvous")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return all rendez-vous")
    void shouldReturnAllRendezVous() throws Exception {

        // Arrange

        RendezVous rdv1 = new RendezVous();
        rdv1.setId(1L);
        rdv1.setRef("RDV-001");

        RendezVous rdv2 = new RendezVous();
        rdv2.setId(2L);
        rdv2.setRef("RDV-002");

        when(rendezVousService.findAll()).thenReturn(List.of(rdv1, rdv2));

        // Act + Assert

        mockMvc.perform(get("/rendezvous/all"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].ref").value("RDV-001"))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].ref").value("RDV-002"));
    }

}