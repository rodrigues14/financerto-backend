package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.wallet.WalletDTO;
import com.lucasdev.financerto.domain.wallet.WalletService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class WalletControllerTest {

    @MockBean
    private WalletService walletService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<WalletDTO> walletDtoJson;

    @Test
    @WithMockUser
    void shouldShowTheWallet() throws Exception {
        WalletDTO walletDTO = new WalletDTO(700.0, 1000.0, 300.0);
        BDDMockito.given(walletService.calculateTotalRevenues(Mockito.any(Authentication.class))).willReturn(walletDTO.totalRevenue());
        BDDMockito.given(walletService.calculateTotalExpenses(Mockito.any(Authentication.class))).willReturn(walletDTO.totalExpense());
        BDDMockito.given(walletService.calculateBalance(Mockito.any(Authentication.class))).willReturn(walletDTO.balance());

        var response = mvc.perform(
                get("/wallet")
        ).andReturn().getResponse();

        var expectedJson = walletDtoJson.write(walletDTO).getJson();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedJson, response.getContentAsString());
    }

}