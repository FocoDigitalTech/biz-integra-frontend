package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_estoque")
public class SetEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estoque;
    private Integer id_produto;
    private Integer id_tecnicosassistentes;
    private Boolean saida_controleproduto;
    private LocalDate data_controle;
    private String quantidade_enviada;
    private String quantidade_devolvida;
    private String quantidade_consumida;
    private String unidade_entrada;
    private String numero_lote;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
