package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_regiao")
public class SetRegiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_regiao;
    private String descricao_regiao;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;

}
