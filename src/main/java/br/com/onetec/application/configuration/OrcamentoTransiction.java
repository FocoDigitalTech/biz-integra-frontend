package br.com.onetec.application.configuration;

import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.model.SetUsuarios;

public class OrcamentoTransiction {

    private static SetOrcamento orcamento;

    public static SetOrcamento getOrcamento() {
        return orcamento;
    }

    public static void setOrcamento(SetOrcamento orcamento) {
        OrcamentoTransiction.orcamento = orcamento;
    }
}
