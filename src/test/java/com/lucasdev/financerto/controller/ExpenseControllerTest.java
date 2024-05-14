package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.expense.*;
import com.lucasdev.financerto.domain.financetransaction.Methods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ExpenseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private JacksonTester<ExpenseDTO> expenseDTOJson;

    @Autowired
    private JacksonTester<ExpenseResponseDTO> expenseResponseDTOJson;

    @MockBean
    private Authentication authentication;

    @Test
    @WithMockUser
    void shouldRegisterExpense() throws Exception {
        ExpenseDTO expenseDTO = new ExpenseDTO(100.0, "Description", LocalDate.now().plusDays(-1), Methods.CHEQUE, "Local", CategoryExpense.OUTRA);
        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO("1",100.0, "Description", LocalDate.now().plusDays(1), Methods.CHEQUE, "Local", CategoryExpense.OUTRA);

        BDDMockito.given(expenseService.registerExpense(any(ExpenseDTO.class), any(Authentication.class)))
                .willReturn(expenseResponseDTO);

        var response = mvc.perform(
                post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expenseDTOJson.write(expenseDTO).getJson())
        ).andReturn().getResponse();

        var expectedJson = expenseResponseDTOJson.write(expenseResponseDTO).getJson();
        var expectedHeaderLocation = "/expense/" + expenseResponseDTO.id();

        Assertions.assertEquals(201, response.getStatus());
        Assertions.assertEquals(expectedJson, response.getContentAsString());
        Assertions.assertTrue(response.getHeader("Location").contains(expectedHeaderLocation));
    }

    @Test
    @WithMockUser
    void shouldDeleteExpense() throws Exception {
        String idExpense = "1";

        expenseService.deleteExpense(idExpense, authentication);

        var response = mvc.perform(
                delete("/expense/{id}", idExpense)
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

}