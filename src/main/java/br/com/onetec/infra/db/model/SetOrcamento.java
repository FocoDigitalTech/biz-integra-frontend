package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tb_orcamento")
public class SetOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "orcamento_seq")
    @TableGenerator(
            name = "orcamento_seq",
            table = "tb_codigonumeracao",
            pkColumnName = "sequence_name",
            valueColumnName = "orcamento_codigonumeracao",
            pkColumnValue = "incremento",
            allocationSize = 1
    )
    private Integer id_orcamento;
    private Integer id_cliente;
    private Integer id_endereco;
    private Integer id_funcionarioinspecao;
    private Integer id_anuncio;
    private String descricao_problema;
    private LocalDate data_orcamento;
    private Integer id_funcionarioatendimento;
    private Integer id_situacao;
    private LocalDate data_inspecao;
    private LocalTime horario_inspecao;
    private Integer id_funcionarioconsultor;
    private Integer id_condicaopagamento;
    private String garantia_orcamento;
    private BigDecimal valor_orcamento;
    private String descricao_permissao;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
