package br.com.onetec.cross.constants;

public enum FinanceiroDataConst {

    STATUS_PREVISTO("Previsão (P)", "P"),
    STATUS_CONSOLIDADO( "Consolidado (C)", "C"),
    STATUS_REAL( "Real (R)", "R");

    private final String valor;
    private final String abreviacao;

    FinanceiroDataConst(String descricao, String codigo) {
        this.valor = descricao;
        this.abreviacao = codigo;
    }

    public String getValor() {
        return valor;
    }

    public String getAbreviacao() {
        return abreviacao;
    }
}
