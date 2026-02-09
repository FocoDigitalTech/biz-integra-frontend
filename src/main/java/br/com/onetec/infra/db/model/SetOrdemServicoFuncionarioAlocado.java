package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_ordemservicofuncionarioalocado")
public class SetOrdemServicoFuncionarioAlocado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordemservicofuncionarioalocado;
    private Integer id_ordemservico;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_funcionario;
    private String descricao_ordemservicofuncionarioalocado;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
