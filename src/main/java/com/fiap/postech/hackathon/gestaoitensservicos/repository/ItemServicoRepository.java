package com.fiap.postech.hackathon.gestaoitensservicos.repository;

import com.fiap.postech.hackathon.gestaoitensservicos.model.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServico, UUID> {
}
