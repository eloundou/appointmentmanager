package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.dto.ResponsableAddRequest;
import com.example.appointmentmanager.dto.ResponsableUpdateRequest;
import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.model.Responsable;
import com.example.appointmentmanager.service.ResponsableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResponsableController.class)
class ResponsableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ResponsableService responsableService;

    @Test
    @DisplayName("Should create responsable successfully")
    void shouldCreateResponsableSuccessfully() throws Exception {

        // Arrange

        Departement departement = new Departement();
        departement.setId(1L);
        departement.setRef("DEP-001");

        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setRef("RESP-001");
        responsable.setEmail("john@test.com");
        responsable.setNom("Doe");
        responsable.setPrenom("John");
        responsable.setService(departement);

        ResponsableAddRequest request = new ResponsableAddRequest(
                "RESP-001", "john@test.com", 677000000, "Doe", "John", "DEP-001");

        when(responsableService.create(any(ResponsableAddRequest.class))).thenReturn(responsable);

        // Act + Assert

        mockMvc.perform(post("/responsables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.ref").value("RESP-001"))
                .andExpect(jsonPath("$.email").value("john@test.com"))
                .andExpect(jsonPath("$.nom").value("Doe"))
                .andExpect(jsonPath("$.prenom").value("John"));
    }

    @Test
    @DisplayName("Should return bad request when create request is invalid")
    void shouldReturnBadRequestWhenCreateRequestIsInvalid() throws Exception {

        // Arrange

        String invalidRequest = """
                {
                    "ref": "",
                    "email": "invalid-email",
                    "telephone": 677000000,
                    "nom": "",
                    "prenom": "John",
                    "refService": ""
                }
                """;

        // Act + Assert

        mockMvc.perform(post("/responsables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should update responsable successfully")
    void shouldUpdateResponsableSuccessfully() throws Exception {

        // Arrange

        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setRef("RESP-001");
        responsable.setEmail("updated@test.com");
        responsable.setNom("Updated");
        responsable.setPrenom("John");

        ResponsableUpdateRequest request = new ResponsableUpdateRequest("updated@test.com", 699999999,
                "Updated", "John");

        when(responsableService.update(anyLong(), any(ResponsableUpdateRequest.class))).thenReturn(responsable);

        // Act + Assert

        mockMvc.perform(put("/responsables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("updated@test.com"))
                .andExpect(jsonPath("$.nom").value("Updated"));
    }

    @Test
    @DisplayName("Should return bad request when update request is invalid")
    void shouldReturnBadRequestWhenUpdateRequestIsInvalid() throws Exception {

        // Arrange

        String invalidRequest = """
                {
                    "email": "invalid-email",
                    "telephone": 699999999,
                    "nom": "Doe",
                    "prenom": "John"
                }
                """;

        // Act + Assert

        mockMvc.perform(put("/responsables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should find responsable by id")
    void shouldFindResponsableById() throws Exception {

        // Arrange

        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setRef("RESP-001");
        responsable.setEmail("john@test.com");
        responsable.setNom("Doe");
        responsable.setPrenom("John");

        when(responsableService.findById(1L))
                .thenReturn(responsable);

        // Act + Assert

        mockMvc.perform(get("/responsables/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.ref").value("RESP-001"))
                .andExpect(jsonPath("$.email").value("john@test.com"));
    }

    @Test
    @DisplayName("Should return all responsables")
    void shouldReturnAllResponsables() throws Exception {

        // Arrange

        Responsable r1 = new Responsable();
        r1.setId(1L);
        r1.setRef("RESP-001");

        Responsable r2 = new Responsable();
        r2.setId(2L);
        r2.setRef("RESP-002");

        when(responsableService.findAll())
                .thenReturn(List.of(r1, r2));

        // Act + Assert

        mockMvc.perform(get("/responsables/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].ref").value("RESP-001"))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].ref").value("RESP-002"));
    }


}