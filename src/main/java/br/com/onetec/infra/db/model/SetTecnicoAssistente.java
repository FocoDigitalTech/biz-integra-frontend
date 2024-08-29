package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_tecnicoassistente")
public class SetTecnicoAssistente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tecnicoassistente;
    private Integer id_assistente;
    private Integer id_tecnico;
    private String nome_tecnicoassistente;
    private String obeservacao_tecnicoassistente;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;

}
