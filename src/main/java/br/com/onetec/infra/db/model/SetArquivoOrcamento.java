package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_arquivosorcamento")
public class SetArquivoOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_arquivosorcamento;
    private Integer id_orcamento;
    private Integer id_cliente;
    private String caminho_arquivoorcamento;
    private String nome_arquivoorcamento;
    private String observacao_arquivoorcamento;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
