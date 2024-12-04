package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_ordemservicomisturas")
public class SetOrdemServicoMisturas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordemservicomisturas;
    private Integer id_ordemservico;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_produto;
    private Integer id_produtosolvente;
    private String numerolote_ordemservicomisturas;
    private Integer quantidadeprevistaproduto_ordemservicomisturas;
    private String unidademedidaproduto_ordemservicomisturas;
    private Integer quantidadeconsumidaproduto_ordemservicomisturas;
    private Integer quantidadeprevistasolvente_ordemservicomisturas;
    private String unidademedidasolvente_ordemservicomisturas;
    private Integer quantidadeconsumidasolvente_ordemservicomisturas;
    private String descricao_ordemservicomisturas;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
