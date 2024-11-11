package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "tb_orcamentoposvenda")
public class SetOrcamentoPosVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_orcamentoposvenda;
    private Integer id_orcamento;
    private Integer id_cliente;
    private String bomatendimento_orcamentoposvenda;
    private String funcionariosuniformizados_orcamentoposvenda;
    private String limpeza_orcamentoposvenda;
    private String duvidas_orcamentoposvenda;
    private String sugestao_orcamentoposvenda;
    private Integer notegeral_orcamentoposvenda;
    private String utilizarianovamente_orcamentoposvenda;
    private LocalDate data_orcamentoposvenda;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
