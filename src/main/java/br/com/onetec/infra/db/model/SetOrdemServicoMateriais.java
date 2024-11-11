package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_ordemservicomateriais")
public class SetOrdemServicoMateriais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordemservicomateriais;
    private Integer id_ordemservico;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_produto;
    private String numerolote_ordemservicomateriais;
    private Integer quantidadeprevista_ordemservicomateriais;
    private Integer quantidadeconsumida_ordemservicomateriais;
    private String descricao_ordemservicomateriais;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
