package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_tipoeventofinanceiro")
public class SetTipoEventoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipoeventofinanceiro;
    private Integer id_tipooperacaofinanceira;
    private String nome_tipoeventofinanceiro;
    private String descricao_tipoeventofinanceiro;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
