package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.revenue.RevenueResponseDTO;
import com.lucasdev.financerto.domain.revenue.RevenueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class RevenueControllerTest {

    @MockBean
    private RevenueService revenueService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RevenueResponseDTO revenueResponseDTO;

    @Autowired
    private JacksonTester<RevenueResponseDTO> revenueResponseDTOJson;

    @Autowired
    private JacksonTester<List<RevenueResponseDTO>> listRevenuesJson;

    @Test
    @WithMockUser
    void shouldFindRevenueById() throws Exception {
        String idRevenue = "1";
        BDDMockito.given(revenueResponseDTO.id()).willReturn(idRevenue);
        BDDMockito.given(revenueService.findRevenueById(eq(idRevenue), any(Authentication.class)))
                .willReturn(revenueResponseDTO);

        var response = mvc.perform(
                get("/revenue/{id}", idRevenue)
        ).andReturn().getResponse();

        var expectedJson = revenueResponseDTOJson.write(revenueResponseDTO).getJson();
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedJson, response.getContentAsString());
    }

    @Test
    @WithMockUser
    void shouldListRevenues() throws Exception {
        RevenueResponseDTO revenueDTO1 = new RevenueResponseDTO("1", 500.0, "Description", null, null, null);
        RevenueResponseDTO revenueDTO2 = new RevenueResponseDTO("2", 900.0, "Description", null, null, null);
        List<RevenueResponseDTO> revenueList = List.of(revenueDTO1, revenueDTO2);
        Page<RevenueResponseDTO> revenuePage = new PageImpl<>(revenueList);

        BDDMockito.given(revenueService.listRevenues(any(Pageable.class), any(Authentication.class))).willReturn(revenuePage);

        var response = mvc.perform(
                get("/revenue")
        ).andReturn().getResponse();

        var expectedJson = listRevenuesJson.write(revenuePage.getContent()).getJson();
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains(expectedJson));
    }

}