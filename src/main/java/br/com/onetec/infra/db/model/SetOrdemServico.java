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
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ordemservico_seq")
    @TableGenerator(
            name = "ordemservico_seq",
            table = "tb_codigonumeracao",
            pkColumnName = "sequence_name",
            valueColumnName = "ordemservico_codigonumeracao",
            pkColumnValue = "incremento",
            allocationSize = 1
    )
    private Integer id_ordemservico;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer id_cliente;
    private Integer id_situacaoservico;
    private Integer id_tipoatendimento;
    private LocalDate datainicio_ordemservico;
    private String diasemanainicio_ordemservico;
    private LocalTime horarioinicio_ordemservico;
    private Integer quantidade_ordemservico;
    private Integer intervalo_ordemservico;
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
    private Integer id_endereco;
    private Integer id_funcionariotecnico;
}
