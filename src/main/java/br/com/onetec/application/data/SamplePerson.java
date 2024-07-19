package br.com.onetec.application.data;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;

@Entity
public class SamplePerson extends AbstractEntity {

    private String endereço;

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public LocalDate getUltimoOrcamento() {
        return ultimoOrcamento;
    }

    public void setUltimoOrcamento(LocalDate ultimoOrcamento) {
        this.ultimoOrcamento = ultimoOrcamento;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getAprovação() {
        return aprovação;
    }

    public void setAprovação(String aprovação) {
        this.aprovação = aprovação;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    private String nome;
    private String contato;
    private String fone;
    private LocalDate ultimoOrcamento;
    private String internet;
    private String aprovação;
    private boolean important;



}
