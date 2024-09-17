package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_subpermissao")
public class SetSubPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_subpermissao;
    private Integer id_permissao;
    private String nome_subtela;
    private String descricao_subtela;
    private String nome_div;
    private Integer escrita;
    private Integer leitura;
    private Integer exclusao;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;

}
