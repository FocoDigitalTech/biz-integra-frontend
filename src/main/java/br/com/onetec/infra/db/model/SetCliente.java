package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tb_cliente")
public class SetCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;
    private String nome_cliente;
    private String nome_fantasia_cliente;
    private String telefone_cliente;
    private String fax_cliente;
    private String email_cliente;
    private String nome_contato_cliente;
    private String cargo_contato_cliente;
    private Integer id_anuncio;
    private Integer id_indicacao;
    private String cpf_cgc_cliente;
    private String iest_cliente;
    private String pf_pj_cliente;
    private String observacoes_cliente;
    private String marca_cliente;
    private String administradora_cliente;
    private String celular_cliente;
    private LocalTime hora_ligacao_cliente;
    private Integer id_tipo_imovel;
    private String endereco_cliente;
    private String numero_res_cliente;
    private String complemento_cliente;
    private String bairro_cliente;
    private String cep_cliente;
    private String cidade_cliente;
    private Integer id_estado;
    private String responsavel_cliente;
    private String ponto_referencia_cliente;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private String ativo;
    private Integer id_usuario;
}
