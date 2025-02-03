package br.com.onetec.infra.db.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_controleproduto")
public class SetControleProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_controleproduto;
    private Integer id_produto;
    private String quantidade_estoque;
    private String quantidade_minima;
    private String utimo_lote;
    private Integer id_fornecedor;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
