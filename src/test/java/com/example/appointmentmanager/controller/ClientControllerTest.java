package com.example.appointmentmanager.controller;

import com.example.appointmentmanager.dto.ClientAddRequest;
import com.example.appointmentmanager.dto.ClientUpdateRequest;
import com.example.appointmentmanager.model.Client;
import com.example.appointmentmanager.service.ClientService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;

    @Test
    @DisplayName("Should create client successfully")
    void shouldCreateClientSuccessfully() throws Exception {

        // Arrange

        Client client = new Client();
        client.setId(1L);
        client.setRef("CLI-001");
        client.setEmail("john@test.com");
        client.setNom("Doe");
        client.setPrenom("John");

        ClientAddRequest request = new ClientAddRequest(
                "CLI-001",
                "john@test.com",
                677000000,
                "Doe",
                "John"
        );

        when(clientService.create(any(ClientAddRequest.class))).thenReturn(client);

        // Act + Assert

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.ref").value("CLI-001"))
                .andExpect(jsonPath("$.email").value("john@test.com"))
                .andExpect(jsonPath("$.nom").value("Doe"))
                .andExpect(jsonPath("$.prenom").value("John"));
    }

    @Test
    @DisplayName("Should return bad request when request is invalid")
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {

        // Arrange

        String invalidRequest = """
                {
                    "ref": "",
                    "email": "invalid-email",
                    "telephone": 677000000,
                    "nom": "",
                    "prenom": "John"
                }
                """;

        // Act + Assert

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should update client successfully")
    void shouldUpdateClientSuccessfully() throws Exception {

        // Arrange

        Client client = new Client();
        client.setId(1L);
        client.setRef("CLI-001");
        client.setEmail("updated@test.com");
        client.setNom("Updated");
        client.setPrenom("John");

        ClientUpdateRequest request = new ClientUpdateRequest(
                "updated@test.com",
                699999999,
                "Updated",
                "John"
        );

        when(clientService.update(any(Long.class), any(ClientUpdateRequest.class)))
                .thenReturn(client);

        // Act + Assert

        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("updated@test.com"))
                .andExpect(jsonPath("$.nom").value("Updated"));
    }

    @Test
    @DisplayName("Should find client by id")
    void shouldFindClientById() throws Exception {

        // Arrange

        Client client = new Client();
        client.setId(1L);
        client.setRef("CLI-001");
        client.setEmail("john@test.com");
        client.setNom("Doe");
        client.setPrenom("John");

        when(clientService.findById(1L)).thenReturn(client);

        // Act + Assert

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.ref").value("CLI-001"))
                .andExpect(jsonPath("$.email").value("john@test.com"));
    }

    @Test
    @DisplayName("Should return all clients")
    void shouldReturnAllClients() throws Exception {

        // Arrange

        Client c1 = new Client();
        c1.setId(1L);
        c1.setRef("CLI-001");

        Client c2 = new Client();
        c2.setId(2L);
        c2.setRef("CLI-002");

        when(clientService.findAll()).thenReturn(List.of(c1, c2));

        // Act + Assert

        mockMvc.perform(get("/clients/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].ref").value("CLI-001"))
                .andExpect(jsonPath("$[1].ref").value("CLI-002"));
    }

}