package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tb_ordemservico")
public class SetOrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordemservico;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_situacaoservico;
    private LocalDate datainicio_ordemservico;
    private String diasemanainicio_ordemservico;
    private LocalTime horarioinicio_ordemservico;
    private String quantidade_ordemservico;
    private String intervalo_ordemservico;
    private String nome_pontofocal;
    private Integer id_funcionarioassistente;
    private String confirmado_ordemservico;
    private Integer id_veiculo;
    private String kminicial_ordemservico;
    private String kmfinal_ordemservico;
    private String ocorrencias_ordemservico;
    private String descricao_ordemservico;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
