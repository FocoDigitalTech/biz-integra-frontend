package br.com.onetec.infra.db.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_produto")
public class SetProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_produto;
    private Integer id_classificacaoproduto;
    private String nome_produto;
    private String unidade_entrada;
    private String unidade_aplicacao;
    private String fator_conversao;
    private String quantidade_estoque;
    private String quantidade_minima;
    private BigDecimal valor_item;
    private String utimo_lote;
    private String grupo_quimico;
    private String principio_ativo;
    private String classificacao_nome;
    private String antidoto_nome;
    private String concentrado_nome;
    private String numero_registro;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;

}
