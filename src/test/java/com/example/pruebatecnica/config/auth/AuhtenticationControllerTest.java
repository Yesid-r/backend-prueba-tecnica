package com.example.pruebatecnica.config.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuhtenticationControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @WithMockUser(roles = "ADMIN")
    void register() {
        RegisterRequest registerRequest = new RegisterRequest("Maria", "Torres", "maria@gmail.com", "1234", "DEUDOR", "31802527152");
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("/api/v1/auth/register", registerRequest, AuthenticationResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void authenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("pedro.perez@example.com", "$2a$10$eA4Qb1M1tOsoDXH56W6yvOzXaIyPHaTB3Pfz1Y/wTt/7F1soGZdI2");
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("/api/v1/auth/authenticate", authenticationRequest, AuthenticationResponse.class);
        System.out.println("response: " + response);
        assertEquals("pedro.perez@example.com", response.getBody().getUser().getEmail());
    }

    @Test
    @WithMockUser(roles = "DEUDOR")
    void registerWithNonAdmin() {
        RegisterRequest registerRequest = new RegisterRequest("Maria", "Torres", "maria@gmail.com", "1234", "DEUDOR", "31802527152");
        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/register", HttpMethod.POST, new HttpEntity<>(registerRequest), AuthenticationResponse.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
