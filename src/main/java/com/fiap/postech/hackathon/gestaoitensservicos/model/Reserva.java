package com.fiap.postech.hackathon.gestaoitensservicos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    private UUID idReserva;

    private Float valorTotal;

    private LocalDate dataInicio;

    private LocalDate dataFim;


    private Integer totalPessoas;

    private Integer totalQuartos;

    @JoinColumn(name="cpf", referencedColumnName = "cpf")
    private String cpf;



}
