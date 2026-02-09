package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tb_orcamentocontato")
public class SetOrcamentoContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_orcamentocontato;
    private Integer id_cliente;
    private Integer id_orcamento;
    private LocalDate data_orcamentocontato;
    private LocalTime horario_orcamentocontato;
    private String nome_orcamentocontato;
    private String telefone_orcamentocontato;
    private Integer id_funcionario;
    private LocalDate dataretorno_orcamentocontato;
    private String unidade_orcamentocontato;
    private String descricao_orcamentocontato;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
