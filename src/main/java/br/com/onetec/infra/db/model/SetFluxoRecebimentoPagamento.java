package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_fluxorecebimentopagamento")
public class SetFluxoRecebimentoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFluxorecebimentopagamento;
    private Integer id_tipoeventofinanceiro;
    private Integer id_eventofinanceiro;
    private Integer id_contacorrente;
    private Integer id_fornecedor;
    private Integer id_tipopagamento;
    private String nomeFluxorecebimentopagamento;
    private String quantidade_parcelas;
    private String quantidade_intervalo;
    private LocalDateTime data_lancamento;
    private BigDecimal valorLancamento;
    private LocalDateTime data_contabil;
    private BigDecimal valor_contabil;
    private LocalDateTime data_pagamento;
    private BigDecimal valor_pagamento;
    private String numero_documento;
    private String numero_parcela;
    private BigDecimal valor_previsto;
    private LocalDateTime datahora_lancamento;
    private LocalDateTime datahora_baixa;
    private BigDecimal valor_baixa;
    private Integer id_funcionariolancamento;
    private Integer id_funcionariobaixa;
    private String statusPagamento;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private String descricao_fluxorecebimentopagamento;
    private LocalDate dataVencimento;
    private Integer id_usuario;
}
