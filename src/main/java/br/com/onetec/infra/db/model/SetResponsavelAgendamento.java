package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_responsavelagendamento")
public class SetResponsavelAgendamento{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_responsavelagendamento;
    private Integer id_cliente;
    private String nome_agendamento;
    private String telefone_fixo;
    private String telefone_celular;
    private String email;
    private String cgc_cpf;
    private String nome_social;
    private String observacao;
    private LocalDate data_agendamento;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
