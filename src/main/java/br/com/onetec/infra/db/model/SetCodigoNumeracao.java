package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_codigonumeracao")
public class SetCodigoNumeracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id_codigonumeracao;
    private BigInteger orcamento_codigonumeracao;
    private BigInteger ordemservico_codigonumeracao;
    private BigInteger contrato_codigonumeracao;
    private BigInteger cliente_codigonumeracao;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
