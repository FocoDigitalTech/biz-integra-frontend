package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_eventofinanceiro")
public class SetEventoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_eventofinanceiro;
    private Integer id_grupoeventofinanceiro;
    private Integer id_tipooperacaofinanceira;
    private String nome_eventofinanceiro;
    private String inform_diarecebimento;
    private String inform_mesrecebimento;
    private BigDecimal valor_eventofinanceiro;
    private String observacoes_eventofinanceiro;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
