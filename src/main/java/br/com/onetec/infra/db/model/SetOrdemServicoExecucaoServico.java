package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_ordemservicoexecucaoservico")
public class SetOrdemServicoExecucaoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordemservicoexecucaoservico;
    private Integer id_ordemservico;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_execucaoservico;
    private BigDecimal valor_ordemservicoexecucaoservico;
    private String garantia_ordemservicoexecucaoservico;
    private String descricao_ordemservicoexecucaoservico;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
