package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_notafiscal")
public class SetNotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_notafiscal;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private String numero_notafiscal;
    private String serie_notafiscal;
    private String chaveacesso_notafiscal;
    private LocalDate dataemissao_notafiscal;
    private String natureza_notafiscal;
    private String unidade_notafiscal;
    private String quantidade_notafiscal;
    private BigDecimal valorunitario_notafiscal;
    private BigDecimal valortotal_notafiscal;
    private String descricao_notafiscal;
    private String caminhoarquivo_notafiscal;
    private String nomearquivo_notafiscal;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
