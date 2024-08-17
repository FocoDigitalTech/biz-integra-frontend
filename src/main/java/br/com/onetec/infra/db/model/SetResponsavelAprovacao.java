package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tb_responsavelaprovacao")
public class SetResponsavelAprovacao{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_responsavelaprovacao;
    private Integer id_cliente;
    private String nome_aprovacao;
    private String telefone_fixo;
    private String telefone_celular;
    private String fax;
    private String email;
    private String cgc_cpf;
    private String inscricao_estatual;
    private String observacao;
    private BigDecimal valor_aprovado;

}
