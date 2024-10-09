package br.com.onetec.application.views.layouts.atendimentosHistorico;

import br.com.onetec.infra.db.model.SetCliente;

public class SetClienteTransiction {

    private static SetCliente cliente;

    public static SetCliente getCliente() {
        return cliente;
    }

    public static void setCliente(SetCliente cliente) {
        SetClienteTransiction.cliente = cliente;
    }
}
