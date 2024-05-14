package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.target.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TargetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TargetService targetService;

    @Autowired
    private JacksonTester<TargetDTO> targetDTOJson;

    @Autowired
    private JacksonTester<TargetResponseDTO> targetResponseDTOJson;

    @Autowired
    private JacksonTester<TargetUpdateDTO> targetUpdateDTOJson;

    @Test
    @WithMockUser
    void shouldRegisterTargetAndCreateTheUri() throws Exception {
        var targetDTO = new TargetDTO("Test", 1000.0, 500.0, LocalDate.now().plusDays(3), CategoryTarget.CARREIRA);
        TargetResponseDTO targetResponseDTO = new TargetResponseDTO("1", "Test", 1000.0, 500.0, LocalDate.now().plusDays(3), CategoryTarget.CARREIRA, null);
        var targetJson = targetDTOJson.write(targetDTO).getJson();

        BDDMockito
                .given(targetService.registerTarget(Mockito.any(Authentication.class), Mockito.any(TargetDTO.class)))
                .willReturn(targetResponseDTO);

        var response = mvc.perform(
                post("/target")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetJson)
        ).andReturn().getResponse();

        var expectedJson = targetResponseDTOJson.write(targetResponseDTO).getJson();
        Assertions.assertEquals(201, response.getStatus());
        Assertions.assertEquals(expectedJson, response.getContentAsString());
        Assertions.assertTrue(response.getHeader("Location").contains("/target/1"));
    }

    @Test
    @WithMockUser
    void shouldUpdateTargetWhenTheDataIsValid() throws Exception {
        TargetUpdateDTO targetUpdateDTO = new TargetUpdateDTO("1", "test", 1000.0, 100.0, LocalDate.now().plusDays(1), CategoryTarget.FINANCAS);
        TargetResponseDTO targetResponseDTO = new TargetResponseDTO("1", "Test", 1000.0, 500.0, LocalDate.now().plusDays(3), CategoryTarget.CARREIRA, "50%");

        BDDMockito.given(targetService.updateTarget(Mockito.any(Authentication.class), Mockito.any(TargetUpdateDTO.class))).willReturn(targetResponseDTO);

        var response = mvc.perform(
                put("/target")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetUpdateDTOJson.write(targetUpdateDTO).getJson())
        ).andReturn().getResponse();

        var expectedJson = targetResponseDTOJson.write(targetResponseDTO).getJson();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedJson, response.getContentAsString());
    }

}