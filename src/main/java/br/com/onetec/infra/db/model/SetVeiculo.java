package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_veiculo")
public class SetVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_veiculo;
    private String nome_veiculo;
    private String marca_veiculo;
    private String modelo_veiculo;
    private String combustivel_veiculo;
    private String placa_veiculo;
    private LocalDate datacompra_veiculo;
    private String nomeregistro_veiculo;
    private String kminicial_veiculo;
    private BigDecimal valorkm_veiculo;
    private LocalDate datavenda_veiculo;
    private BigDecimal valorvenda_veiculo;
    private BigDecimal valorcompra_veiculo;
    private String renavam_veiculo;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
