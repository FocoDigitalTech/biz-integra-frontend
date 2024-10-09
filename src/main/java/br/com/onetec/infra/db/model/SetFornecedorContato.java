package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_fornecedorcontato")
public class SetFornecedorContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_fornecedorcontato;
    private Integer id_fornecedor;
    private String nome_fornecedorcontato;
    private String cargo_fornecedorcontato;
    private String departamento_fornecedorcontato;
    private String telefone_fornecedorcontato;
    private String email_fornecedorcontato;
    private String observacoes_fornecedorcontato;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;

}
