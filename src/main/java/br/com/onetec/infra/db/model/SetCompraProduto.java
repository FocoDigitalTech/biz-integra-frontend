package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_compraproduto")
public class SetCompraProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_compraproduto;
    private Integer id_compra;
    private Integer id_produto;
    private Integer quantidade_compraproduto;
    private BigDecimal valorunitario_compraproduto;
    private BigDecimal valortotal_compraproduto;
    private String numerolote_compraproduto;
    private LocalDate datafabricacao_compraproduto;
    private LocalDate datavalidade_compraproduto;
    private LocalDate datarecebimento_compraproduto;
    private String responsavelrecebimento_compraproduto;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
