package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_comissoes")
public class SetComissoes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_comissoes;
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_funcionario;
    private Integer id_orcamento;
    private String parcelas_comissoes;
    private BigDecimal porcentagem_comissoes;
    private LocalDate data_comissao;
    private BigDecimal valor_comissao;
    private Integer parcela_comisao;
    private Integer totalparcelas_comissao;
    private LocalDate datapagamento_comissao;
    private BigDecimal valortotal_comissao;
    private String descricao_comissao;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;

}
