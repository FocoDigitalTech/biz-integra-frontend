package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_tipomidia")
public class SetTipoMidia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipomidia;
    private String descricao_tipomidia;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
