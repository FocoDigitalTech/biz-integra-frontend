package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_contacorrente")
public class SetContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_contacorrente;
    private String nome_contacorrente;
    private String banco_contacorrente;
    private String agencia_contacorrente;
    private String numero_contacorrente;
    private BigDecimal limete_contacorrente;
    private LocalDateTime ultimolancamento_contacorrente;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
