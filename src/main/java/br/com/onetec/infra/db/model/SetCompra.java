package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tb_compra")
public class SetCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_compra;
    private Integer id_fornecedor;
    private String notafiscal_compra;
    private Integer id_condicaopagamento;
    private Integer id_contacorrente;
    private String numeronotafiscal_compra;
    private LocalDate datanotafiscal_compra;
    private LocalDate datapagamento_compra;
    private BigDecimal valoritemstotal_compra;
    private BigDecimal valorfrete_compra;
    private BigDecimal valordesconto_compra;
    private BigDecimal valortotal_compra;
    private String observacoes_compra;
    private String responsavelaprovacao_compra;
    private LocalDate dataaprovacao_compra;
    private LocalTime horario_compra;
    private LocalDate datavalidate_compra;
    private LocalDate data_compra;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
