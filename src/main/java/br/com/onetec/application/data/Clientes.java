package br.com.onetec.application.data;

public class Clientes {

    public String getUltimoOrcamento() {
        return ultimoOrcamento;
    }

    public void setUltimoOrcamento(String ultimoOrcamento) {
        this.ultimoOrcamento = ultimoOrcamento;
    }

    String ultimoOrcamento;

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

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
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

    String nome;
    String contato;
    String fone;
    String endereço;
    String internet;
    String aprovação;
}
