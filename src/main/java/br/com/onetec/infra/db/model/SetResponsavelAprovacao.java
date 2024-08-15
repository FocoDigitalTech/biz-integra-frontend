package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_responsavelaprovacao")
public class SetResponsavelAprovacao{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_responsavelaprovacao;
    private Integer id_cliente;
    private Integer nome_aprovacao;
    private Integer telefone_fixo;
    private Integer telefone_celular;
    private Integer fax;
    private Integer email;
    private Integer cgc_cpf;
    private Integer inscricao_estatual;
    private Integer observacao;
    private Integer valor_aprovado;

}
