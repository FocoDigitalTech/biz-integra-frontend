package br.com.onetec.infra.db.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_praga")
public class SetPraga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_praga;
    private Integer codigo_praga;
    private String descricao_praga;
    private String nomecientifico_praga;
    private String caminhofoto_praga;
    private String nomefoto_praga;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;

}
