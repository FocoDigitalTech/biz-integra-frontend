package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_responsavelcobranca")
public class SetResponsavelCobranca{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_responsavelcobranca;
    private Integer id_cliente;
    private String nome_cobranca;
    private String telefone_fixo;
    private String telefone_celular;
    private String email;
    private String cgc_cpf;
    private String nome_social;
    private String observacao;
    private BigDecimal valor_cobranca;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;

}