package com.fiap.postech.hackathon.gestaoitensservicos.controller;

import com.fiap.postech.hackathon.gestaoitensservicos.model.ItemServico;
import com.fiap.postech.hackathon.gestaoitensservicos.service.ItemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/gestaoItens")
@RestController
public class ItemServicoController {

    @Autowired
    private ItemServicoService itemServicoService;
    @GetMapping
    public ResponseEntity<?> obterItensServicosDisponiveis(){
        return ResponseEntity.status(HttpStatus.OK).body(itemServicoService.obterItensServicosDisponiveis());
    }

    @GetMapping("/{idItemServico}")
    public ResponseEntity<?> obterItensServicosDisponiveis(@PathVariable UUID idItemServico){
        return ResponseEntity.status(HttpStatus.OK).body(itemServicoService.obterItemServicoPorId(idItemServico));
    }

    @DeleteMapping("/{idItemServico}")
    public ResponseEntity<?> deletarItemServico(@PathVariable UUID idItemServico){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(itemServicoService.deletarItemServico(idItemServico));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{idItemServico}")
    public ResponseEntity<?> atualizarItemServico(@PathVariable UUID idItemServico, @RequestBody ItemServico itemServico){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(itemServicoService.atualizarItemServico(idItemServico, itemServico));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping()
    public ResponseEntity<?> cadastrarItemServico(@RequestBody ItemServico itemServico){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(itemServicoService.cadastrarItemServico(itemServico));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
