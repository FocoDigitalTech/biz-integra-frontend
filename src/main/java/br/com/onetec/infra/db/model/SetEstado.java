package br.com.onetec.infra.db.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_estado")
public class SetEstado{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estado;
    private String uf_estado;
    private String nome_estado;
}
