package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_pagamento")
public class SetPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pagamento;
    private Integer id_cliente;
    private Integer id_orcamento;
    private Integer id_contrato;
    private Integer numeroparcela_pagamento;
    private Integer totalparcela_pagamento;
    private LocalDate vencimento_pagamento;
    private BigDecimal valor_pagamento;
    private LocalDate data_pagamento;
    private BigDecimal valorpago_pagamento;
    private String numerodocumento_pagamento;
    private Integer id_ordemservico;
    private Integer id_tipopagamento;
    private String numero_pagamento;
    private String chavepix_pagamento;
    private String pixtoken_pagamento;
    private String agencia_pagamento;
    private String conta_pagamento;
    private String banco_pagamento;
    private String boleto_pagamento;
    private Integer id_situacaopagamento;
    private String descricao_pagamento;
    private Integer id_usuariobaixa;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
    private String baixado;
}
