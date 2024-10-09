package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_contrato")
public class SetContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_orcamento;
    private String aplicacoes_periodicas;
    private BigDecimal valor_total;
    private BigDecimal valor_nagasaki;
    private LocalDate data_venda;
    private String tipo_cobranca;
    private Integer id_condicaopagamento;
    private LocalDate datainicio_execucao;
    private LocalDate datainicio_vencimento;
    private Integer meses_garantia;
    private LocalDate datafim_garantia;
    private Integer quantidade_aplicacoes;
    private String observacoes_contrato;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
