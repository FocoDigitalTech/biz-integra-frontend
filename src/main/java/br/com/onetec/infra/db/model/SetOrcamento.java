package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_orcamento")
public class SetOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_orcamento;
    private Integer id_cliente;
    private Integer id_endereco;
    private Integer descricao_problema;
    private Integer data_orcamento;
    private Integer id_funcionarioatendimento;
    private Integer id_situacao;
    private Integer data_inspecao;
    private Integer horario_inspecao;
    private Integer id_funcionarioconsultor;
    private Integer id_condicaopagamento;
    private Integer garantia_orcamento;
    private Integer valor_orcamento;
    private Integer descricao_permissao;
    private Integer data_inclusao;
    private Integer data_alteracao;
    private Integer data_exclusao;
    private Integer id_usuario;
    private Integer ativo;
}
