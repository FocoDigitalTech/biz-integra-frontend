package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_servico")
public class SetServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_servico;
    private Integer codigo_servico;
    private String descricao_servico;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
