package com.fiap.postech.hackathon.gestaoitensservicos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ItemServico {
    @Id
    private UUID idItemServico;
    @Min(value=1, message="Valor do item ou serviço não pode ser igual ou menor que zero!")
    private Float valorItemServico;
    @NotBlank( message="Campo descritivo do item ou serviço não pode estar vazio")
    private String descritivoItemServico;
    private LocalDate dataCompraItemServico;

    @ManyToOne
    @JoinColumn(name = "idReserva", referencedColumnName = "idReserva")
    private Reserva reserva;

}
