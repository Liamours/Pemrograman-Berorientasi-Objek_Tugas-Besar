package com.example.growandcheer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Util: baca file JSON dari classpath
    private String readJson(String classpathLocation) throws Exception {
        ClassPathResource res = new ClassPathResource(classpathLocation);
        byte[] bytes = Files.readAllBytes(Path.of(res.getURI()));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Test
    void loginWithEmptyInput_returnsBadRequest() throws Exception {
        String requestJson  = readJson("requests/request_empty_bf.json");   // atau just "{}"
        String expectedJson = readJson("responses/response_empty_bf.json");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void loginWithWrongCredentials_returnsUnauthorized() throws Exception {
        String requestJson  = readJson("requests/request_fb.json");             // payload valid
        String expectedJson = readJson("responses/response_wrong_bf.json");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void loginSuccess_returnsUserData() throws Exception {
        // asumsi kamu sudah punya user "alice@example.com" via @BeforeEach atau data.sql
        String requestJson  = readJson("requests/request_fb.json");
        String expectedJson = readJson("responses/response_success_bf.json");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
