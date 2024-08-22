package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_funcionario")
public class SetFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_funcionario;
    private Integer id_departamento;
    private String nome_funcionario;
    private String nome_carteira;
    private String endereco_funcionario;
    private String numeroimovel_funcionario;
    private String complemento_funcionario;
    private String bairro_funcionario;
    private String cep_funcionario;
    private String cidade_funcionario;
    private Integer id_estado;
    private String celular_funcionario;
    private String rg_funcionario;
    private String cpf_funcionario;
    private String titulo_eleitor;
    private String reservista_militar;
    private String numero_ctps;
    private String serie_ctps;
    private String pis_funcionario;
    private String cnh_funcionario;
    private LocalDate vencimento_cnh;
    private LocalDate data_admissao;
    private LocalDate data_desligamento;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
}
