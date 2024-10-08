package br.com.onetec.application.model;

import br.com.onetec.infra.db.model.SetEstado;
import br.com.onetec.infra.db.model.SetRegiao;
import br.com.onetec.infra.db.model.SetTipoImovel;

public class Endereco {

    public Endereco(String fieldEnderecosCEP, SetTipoImovel comboEnderecosTipoImovel, String fieldEnderecosArea, String fieldEnderecosEndereço, String fieldEnderecosNumero, String fieldEnderecosComplemento, String fieldEnderecosBairro, String fieldEnderecosCidade, SetEstado comboEnderecosUF, String fieldEnderecosTelefone, String fieldEnderecosPagGuia, String fieldEnderecosReponsavel, SetRegiao comboEnderecosRegiao, String fieldEnderecosPontodeReferencia) {
        this.fieldEnderecosCEP = fieldEnderecosCEP;
        this.comboEnderecosTipoImovel = comboEnderecosTipoImovel;
        this.fieldEnderecosArea = fieldEnderecosArea;
        this.fieldEnderecosEndereço = fieldEnderecosEndereço;
        this.fieldEnderecosNumero = fieldEnderecosNumero;
        this.fieldEnderecosComplemento = fieldEnderecosComplemento;
        this.fieldEnderecosBairro = fieldEnderecosBairro;
        this.fieldEnderecosCidade = fieldEnderecosCidade;
        this.comboEnderecosUF = comboEnderecosUF;
        this.fieldEnderecosTelefone = fieldEnderecosTelefone;
        this.fieldEnderecosPagGuia = fieldEnderecosPagGuia;
        this.fieldEnderecosReponsavel = fieldEnderecosReponsavel;
        this.comboEnderecosRegiao = comboEnderecosRegiao;
        this.fieldEnderecosPontodeReferencia = fieldEnderecosPontodeReferencia;
    }

    public String getFieldEnderecosCEP() {
        return fieldEnderecosCEP;
    }

    public void setFieldEnderecosCEP(String fieldEnderecosCEP) {
        this.fieldEnderecosCEP = fieldEnderecosCEP;
    }

    public SetTipoImovel getComboEnderecosTipoImovel() {
        return comboEnderecosTipoImovel;
    }

    public void setComboEnderecosTipoImovel(SetTipoImovel comboEnderecosTipoImovel) {
        this.comboEnderecosTipoImovel = comboEnderecosTipoImovel;
    }

    public String getFieldEnderecosArea() {
        return fieldEnderecosArea;
    }

    public void setFieldEnderecosArea(String fieldEnderecosArea) {
        this.fieldEnderecosArea = fieldEnderecosArea;
    }

    public String getFieldEnderecosEndereço() {
        return fieldEnderecosEndereço;
    }

    public void setFieldEnderecosEndereço(String fieldEnderecosEndereço) {
        this.fieldEnderecosEndereço = fieldEnderecosEndereço;
    }

    public String getFieldEnderecosNumero() {
        return fieldEnderecosNumero;
    }

    public void setFieldEnderecosNumero(String fieldEnderecosNumero) {
        this.fieldEnderecosNumero = fieldEnderecosNumero;
    }

    public String getFieldEnderecosComplemento() {
        return fieldEnderecosComplemento;
    }

    public void setFieldEnderecosComplemento(String fieldEnderecosComplemento) {
        this.fieldEnderecosComplemento = fieldEnderecosComplemento;
    }

    public String getFieldEnderecosBairro() {
        return fieldEnderecosBairro;
    }

    public void setFieldEnderecosBairro(String fieldEnderecosBairro) {
        this.fieldEnderecosBairro = fieldEnderecosBairro;
    }

    public String getFieldEnderecosCidade() {
        return fieldEnderecosCidade;
    }

    public void setFieldEnderecosCidade(String fieldEnderecosCidade) {
        this.fieldEnderecosCidade = fieldEnderecosCidade;
    }

    public SetEstado getComboEnderecosUF() {
        return comboEnderecosUF;
    }

    public void setComboEnderecosUF(SetEstado comboEnderecosUF) {
        this.comboEnderecosUF = comboEnderecosUF;
    }

    public String getFieldEnderecosTelefone() {
        return fieldEnderecosTelefone;
    }

    public void setFieldEnderecosTelefone(String fieldEnderecosTelefone) {
        this.fieldEnderecosTelefone = fieldEnderecosTelefone;
    }

    public String getFieldEnderecosPagGuia() {
        return fieldEnderecosPagGuia;
    }

    public void setFieldEnderecosPagGuia(String fieldEnderecosPagGuia) {
        this.fieldEnderecosPagGuia = fieldEnderecosPagGuia;
    }

    public String getFieldEnderecosReponsavel() {
        return fieldEnderecosReponsavel;
    }

    public void setFieldEnderecosReponsavel(String fieldEnderecosReponsavel) {
        this.fieldEnderecosReponsavel = fieldEnderecosReponsavel;
    }

    public SetRegiao getComboEnderecosRegiao() {
        return comboEnderecosRegiao;
    }

    public void setComboEnderecosRegiao(SetRegiao comboEnderecosRegiao) {
        this.comboEnderecosRegiao = comboEnderecosRegiao;
    }

    public String getFieldEnderecosPontodeReferencia() {
        return fieldEnderecosPontodeReferencia;
    }

    public void setFieldEnderecosPontodeReferencia(String fieldEnderecosPontodeReferencia) {
        this.fieldEnderecosPontodeReferencia = fieldEnderecosPontodeReferencia;
    }

    private String fieldEnderecosCEP;
    private SetTipoImovel comboEnderecosTipoImovel;
    private String fieldEnderecosArea;
    private String fieldEnderecosEndereço;
    private String fieldEnderecosNumero;
    private String fieldEnderecosComplemento;
    private String fieldEnderecosBairro;
    private String fieldEnderecosCidade;
    private SetEstado comboEnderecosUF;
    private String fieldEnderecosTelefone;
    private String fieldEnderecosPagGuia;
    private String fieldEnderecosReponsavel;
    private SetRegiao comboEnderecosRegiao;
    private String fieldEnderecosPontodeReferencia;
}

