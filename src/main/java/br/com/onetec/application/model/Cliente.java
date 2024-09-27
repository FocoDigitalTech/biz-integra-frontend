package br.com.onetec.application.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Cliente {

    private String nomeField;
    private String teste;
    private String telefoneField;
    private String celularField;
    private LocalDate dataField;
    private LocalTime horaField;
    private String contatoField;
    private String administradora;
    private String tipoMidia;
    private String nomeIndicacaoField;
    private String faxField;
    private String internetEmailField;
    private String FJField;
    private String CGCCPFField;
    private String cnpjField;
    private String inscEstatualField;
    private String observacaoField;
}
