package com.fiap.postech.hackathon.gestaoitensservicos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.postech.hackathon.gestaoitensservicos.config.ItemServicoGenerator;
import com.fiap.postech.hackathon.gestaoitensservicos.model.ItemServico;
import com.fiap.postech.hackathon.gestaoitensservicos.service.ItemServicoService;
import com.fiap.postech.hackathon.gestaoitensservicos.service.ItemServicoServiceTest;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers= ItemServicoController.class)
public class ItemServicoControllerTest {

    @MockBean
    private ItemServicoService itemServicoService;

    private ItemServico mockItemServico;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockItemServico = ItemServicoGenerator.geraItemServico();
    }

    @Test
    public void devePermitirCadastrarItemServico() throws Exception {
        Mockito.when(itemServicoService.cadastrarItemServico(Mockito.any())).thenReturn(mockItemServico);

        mockMvc.perform(MockMvcRequestBuilders.post("/gestaoItens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ItemServicoGenerator.geraItemServicoJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.idItemServico").value(mockItemServico.getIdItemServico().toString()));

    }


    @Test
    public void deveRetornarErroQuandoValorItemZerado() throws Exception {
        Mockito.when(itemServicoService.cadastrarItemServico(Mockito.any())).thenThrow(new ConstraintViolationException("Valor do item não pode ser zero", null, "Valor Item"));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/gestaoItens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ItemServicoGenerator.geraItemServicoJson()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String responseContent = result.getResponse().getContentAsString();

        assertTrue(responseContent.contains("Valor do item não pode ser zero"));
    }


    @Test
    public void devePermitirObterItensOuServicos() throws Exception {
        List<ItemServico> listaItemServico = new ArrayList<>();
        listaItemServico.add(ItemServicoGenerator.geraItemServico());
        listaItemServico.add(ItemServicoGenerator.geraItemServico());
        Mockito.when(itemServicoService.obterItensServicosDisponiveis()).thenReturn(listaItemServico);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/gestaoItens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ItemServicoGenerator.geraItemServicoJson()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();


        assertTrue(result.getResponse().getContentAsString().contains(listaItemServico.get(0).getIdItemServico().toString()));

    }

    @Test
    public void devePermitirDeletarItemServico() throws Exception {
        Mockito.when(itemServicoService.deletarItemServico(Mockito.any())).thenReturn(mockItemServico.getIdItemServico());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/gestaoItens/" + mockItemServico.getIdItemServico()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(mockItemServico.getIdItemServico().toString()));
    }


    @Test
    public void deveRetornarErroQuandoDeletarItemServicoIdInvalido() throws Exception {
        Mockito.when(itemServicoService.deletarItemServico(Mockito.any())).thenThrow(new RuntimeException("Item não encontrado"));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/gestaoItens/" + mockItemServico.getIdItemServico()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void devePermitirAtualizarItemServico() throws Exception {
        Mockito.when(itemServicoService.atualizarItemServico(Mockito.any(), Mockito.any())).thenReturn(mockItemServico);

        mockMvc.perform(MockMvcRequestBuilders.put("/gestaoItens/" + mockItemServico.getIdItemServico())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ItemServicoGenerator.geraItemServicoJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.idItemServico").value(mockItemServico.getIdItemServico().toString()));
    }

    @Test
    public void deveRetornarErroQuandoAtualizarItemServicoValorZerado() throws Exception {
        Mockito.when(itemServicoService.atualizarItemServico(Mockito.any(), Mockito.any())).thenThrow(new ConstraintViolationException("Valor do item não pode ser zero", null, "Valor Item"));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put("/gestaoItens/" + mockItemServico.getIdItemServico())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ItemServicoGenerator.geraItemServicoJson()))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Assertions.assertTrue(responseContent.contains("Valor do item não pode ser zero"));

    }



}
