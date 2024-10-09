package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_situacaopagamento")
public class SetSituacaoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_situacaopagamento;
    private String nome_situacaopagamento;
    private String descricao_situacaopagamento;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
