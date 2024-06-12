package com.example.pruebatecnica.controller;

import com.example.pruebatecnica.config.auth.AuthenticationResponse;
import com.example.pruebatecnica.config.auth.RegisterRequest;
import com.example.pruebatecnica.user.UserDTO;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void findAll() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/users", String.class);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int usersCount = documentContext.read("$.length()");
        assertEquals(3, usersCount);
    }

    @Test
    void findByRole() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/users/find-usersrole/DEUDOR", String.class);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int usersCount = documentContext.read("$.length()");
        assertEquals(1, usersCount);
    }

    @Test
    void deleteUser() {

        Integer userId = 4;
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/users/" + userId, String.class);
        System.out.println("get response: " + getResponse);
        assertEquals(200, getResponse.getStatusCodeValue());
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/v1/users/" + userId, HttpMethod.DELETE, null, Void.class);
        System.out.println("delete response: " + deleteResponse);
        assertEquals(204, deleteResponse.getStatusCodeValue());

    }

    @Test
    void updateUser() {


        // Update the created user
        Integer userId = 3;
        UserDTO updatedUser = new UserDTO("Updated", "User", "alice.smith@example.com", "312312");
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(updatedUser);
        ResponseEntity<UserDTO> updateResponse = restTemplate.exchange("/api/v1/users/" + userId, HttpMethod.PUT, requestEntity, UserDTO.class);
        assertNotNull(updateResponse.getBody());
        assertEquals("Updated", updateResponse.getBody().getFirstname());
        assertEquals("alice.smith@example.com", updateResponse.getBody().getEmail());
    }


}
