package com.fiap.postech.hackathon.gestaoitensservicos.service;

import com.fiap.postech.hackathon.gestaoitensservicos.model.ItemServico;
import com.fiap.postech.hackathon.gestaoitensservicos.repository.ItemServicoRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemServicoService {
    @Autowired
    private ItemServicoRepository itemServicoRepository;

    @Autowired
    private Validator validator;
    public List<ItemServico> obterItensServicosDisponiveis() {
       return itemServicoRepository.findAll();
    }

    public UUID deletarItemServico(UUID idItemServico) {
        itemServicoRepository.deleteById(idItemServico);
        return idItemServico;
    }

    public ItemServico atualizarItemServico(UUID idItemServico, ItemServico itemServico) {
        var violations = validator.validate(itemServico);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var itemServicoAtualizado = itemServicoRepository.getReferenceById(idItemServico);
        itemServicoAtualizado.setDataCompraItemServico(itemServico.getDataCompraItemServico());
        itemServicoAtualizado.setDescritivoItemServico(itemServico.getDescritivoItemServico());
        itemServicoAtualizado.setValorItemServico(itemServico.getValorItemServico());
        return itemServicoRepository.save(itemServicoAtualizado);
    }

    public ItemServico cadastrarItemServico(ItemServico itemServico) {
        var violations = validator.validate(itemServico);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        itemServico.setIdItemServico(UUID.randomUUID());
        return itemServicoRepository.save(itemServico);
    }

    public ItemServico obterItemServicoPorId(UUID idItemServico) {
        return itemServicoRepository.findById(idItemServico).get();
    }
}
