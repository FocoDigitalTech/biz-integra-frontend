package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "tb_dadosempresa")
public class SetDadosEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_dadosempresa;
    private String nome_dadosempresa;
    private String endereco_dadosempresa;
    private String bairro_dadosempresa;
    private String cep_dadosempresa;
    private String cidade_dadosempresa;
    private String estado_dadosempresa;
    private String telefone_dadosempresa;
    private String celular_dadosempresa;
    private String email_dadosempresa;
    private String cnpj_dadosempresa;
    private String agencia_dadosempresa;
    private String conta_dadosempresa;
    private LocalDate dataestoque_dadosempresa;
    private String nomequimico_dadosempresa;
    private String numeroalvaraquimico_dadosempresa;
    private String telefonequimico_dadosempresa;
    private String celularquimico_dadosempresa;
    private String emailquimico_dadosempresa;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
