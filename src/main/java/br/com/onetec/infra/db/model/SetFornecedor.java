package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_fornecedores")
public class SetFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_fornecedor;
    private LocalDate data_cadastro;
    private Integer id_setoratuacao;
    private String tipo_naturezajuridica;
    private String numero_naturezajuridica;
    private String razaosocial_fornecedor;
    private String nomefantasia_fornecedor;
    private String cep_fornecedor;
    private String endereco_fornecedor;
    private String numero_endereco;
    private String bairro_fornecedor;
    private String cidade_fornecedor;
    private Integer id_estado;
    private String telefone_fornecedor;
    private String email_fornecedor;
    private String nomecontato_fornecedor;
    private String cargocontato_fornecedor;
    private String inscicaoestadual_fornecedor;
    private String observacao_fornecedor;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
