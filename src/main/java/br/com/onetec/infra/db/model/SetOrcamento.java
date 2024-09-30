package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tb_orcamento")
public class SetOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_orcamento;
    private Integer id_cliente;
    private Integer id_endereco;
    private String descricao_problema;
    private LocalDate data_orcamento;
    private Integer id_funcionarioatendimento;
    private Integer id_situacao;
    private LocalDate data_inspecao;
    private LocalTime horario_inspecao;
    private Integer id_funcionarioconsultor;
    private Integer id_condicaopagamento;
    private String garantia_orcamento;
    private BigDecimal valor_orcamento;
    private String descricao_permissao;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
