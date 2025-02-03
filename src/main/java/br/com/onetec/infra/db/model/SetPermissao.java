package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_permissao")
public class SetPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_permissao;
    private Integer id_grupousuario;
    private Integer id_tela;
    private String descricao_permissao;
    private String tela_view;
    private String nome_tela;
    private Integer escrita;
    private Integer leitura;
    private Integer exclusao;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
