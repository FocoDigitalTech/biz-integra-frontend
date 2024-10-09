package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_faturamento")
public class SetFaturamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_faturamento;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private String nome_faturamento;
    private String endereco_faturamento;
    private String bairro_faturamento;
    private String cep_faturamento;
    private String cidade_faturamento;
    private String estado_faturamento;
    private String pfpj_faturamento;
    private String cpfcnpf_faturamento;
    private String incricaoestadual_faturamento;
    private String observacao_faturamento;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
