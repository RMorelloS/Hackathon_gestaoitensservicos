package com.fiap.postech.hackathon.gestaoitensservicos.service;


import com.fiap.postech.hackathon.gestaoitensservicos.config.ItemServicoGenerator;
import com.fiap.postech.hackathon.gestaoitensservicos.model.ItemServico;
import com.fiap.postech.hackathon.gestaoitensservicos.repository.ItemServicoRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@WebMvcTest(controllers=ItemServicoService.class)
public class ItemServicoServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemServicoRepository itemServicoRepository;


    @Autowired
    private ItemServicoService itemServicoService;


    private ItemServico mockItemServico;

    public ItemServicoServiceTest(){
        mockItemServico = ItemServicoGenerator.geraItemServico();
    }

    @Test
    public void devePermitirCadastrarItemServico(){
        Mockito.when(itemServicoRepository.save(Mockito.any())).thenReturn(mockItemServico);

        var resultado = itemServicoService.cadastrarItemServico(mockItemServico);
        assertEquals(mockItemServico.getIdItemServico(), resultado.getIdItemServico());
    }

    @Test
    public void deveRetornarErroQuandoCadastrarItemServicoValorZerado(){
        var itemServicoValorZerado = mockItemServico;
        itemServicoValorZerado.setValorItemServico(0.0f);
        assertThrows(ConstraintViolationException.class, () -> {
            itemServicoService.cadastrarItemServico(mockItemServico);
        });

    }


    @Test
    public void devePermitirObterItensServicos(){
        List<ItemServico> listaItemServico = new ArrayList<>();
        Mockito.when(itemServicoRepository.findAll()).thenReturn(listaItemServico);
        var resultado = itemServicoService.obterItensServicosDisponiveis();
        assertEquals(listaItemServico, resultado);
    }

    @Test
    public void devePermitirDeletarItemServico(){
        doNothing().when(itemServicoRepository).deleteById(Mockito.any());

        var resultado = itemServicoService.deletarItemServico(mockItemServico.getIdItemServico());
        assertEquals(resultado, mockItemServico.getIdItemServico());
    }

    @Test
    public void devePermitirAtualizarItemServico() throws Exception{
        var itemServicoAtualizado = mockItemServico;
        itemServicoAtualizado.setValorItemServico(15000.32f);
        Mockito.when(itemServicoRepository.save(Mockito.any())).thenReturn(itemServicoAtualizado);
        Mockito.when(itemServicoRepository.getReferenceById(Mockito.any())).thenReturn(mockItemServico);

        var resultado = itemServicoService.atualizarItemServico(mockItemServico.getIdItemServico(), mockItemServico);
        assertEquals(resultado, itemServicoAtualizado);
    }

    @Test
    public void deveRetornarErroQuandoAtualizarItemServicoValorZerado() throws Exception{
        var itemServicoAtualizado = mockItemServico;
        itemServicoAtualizado.setValorItemServico(0.0f);

        assertThrows(ConstraintViolationException.class, () -> {
                    itemServicoService.atualizarItemServico(itemServicoAtualizado.getIdItemServico(), itemServicoAtualizado);
                }
        );
    }




}
