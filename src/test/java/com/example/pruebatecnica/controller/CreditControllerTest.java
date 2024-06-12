package com.example.pruebatecnica.controller;

import com.example.pruebatecnica.model.Credito;
import com.example.pruebatecnica.model.DTO.RegisterCredit;
import com.example.pruebatecnica.model.DTO.PaymentDTO;
import com.example.pruebatecnica.user.User;
import com.example.pruebatecnica.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CreditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testFindAllCredits() throws Exception {
        mockMvc.perform(get("/api/v1/credit"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testRegisterCredit() throws Exception {
        RegisterCredit registerCredit = new RegisterCredit(3, 4, 500.00);

        mockMvc.perform(post("/api/v1/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerCredit)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testFindCredit() throws Exception {
        mockMvc.perform(get("/api/v1/credit/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testDeleteCredit() throws Exception {
        mockMvc.perform(delete("/api/v1/credit/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/credit/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindByDeudor() throws Exception {
        mockMvc.perform(get("/api/v1/credit/credits-deudor/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testFindByCobrador() throws Exception {
        mockMvc.perform(get("/api/v1/credit/credits-cobrador/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testAddPayment() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO(2, 500.00);

        mockMvc.perform(post("/api/v1/credit/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paymentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    private String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
