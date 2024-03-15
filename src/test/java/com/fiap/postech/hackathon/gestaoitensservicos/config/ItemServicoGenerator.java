package com.fiap.postech.hackathon.gestaoitensservicos.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.postech.hackathon.gestaoitensservicos.model.ItemServico;

import java.time.LocalDate;
import java.util.UUID;

public class ItemServicoGenerator {
    public static ItemServico geraItemServico(){
        var itemServico = new ItemServico();
        itemServico.setIdItemServico(UUID.fromString("5b7f4a8b-7d15-4e83-b4c5-076a9c0cf948"));
        itemServico.setValorItemServico(100.50f);
        itemServico.setDescritivoItemServico("Descrição do item ou serviço");
        itemServico.setDataCompraItemServico(LocalDate.parse("2024-03-13"));
        return itemServico;
    }

    public static String geraItemServicoJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(geraItemServico());
    }
}
