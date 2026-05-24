package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.model.Departement;
import com.example.appointmentmanager.service.DepartementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartementService departementService;

    @Test
    @DisplayName("Should return all departements")
    void shouldReturnAllDepartements() throws Exception {

        // Arrange

        Departement d1 = new Departement();
        d1.setId(1L);
        d1.setRef("CARDIO");
        d1.setNom("Cardiology");

        Departement d2 = new Departement();
        d2.setId(2L);
        d2.setRef("RADIO");
        d2.setNom("Radiology");

        when(departementService.findAll()).thenReturn(List.of(d1, d2));

        // Act + Assert

        mockMvc.perform(get("/services/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].ref").value("CARDIO"))
                .andExpect(jsonPath("$[0].nom").value("Cardiology"))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].ref").value("RADIO"))
                .andExpect(jsonPath("$[1].nom").value("Radiology"));
    }

}