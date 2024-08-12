package br.com.onetec.application.service.clientesservice;

import br.com.onetec.application.data.Clientes;
import br.com.onetec.application.views.main.clientes.ClientesView;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientesService {

    public List<Clientes> list(PageRequest of, ClientesView.Filters filters) {
        List<Clientes> lista = new ArrayList<>();
        lista.add(novoCliente());
        lista.add(novoCliente());
        lista.add(novoCliente());
        lista.add(novoCliente());

        return lista;
    }

    private Clientes novoCliente() {
        Clientes cliente = new Clientes();
        cliente.setFone("12");
        cliente.setAprovação("Aprovado");
        cliente.setContato("1233");
        cliente.setEndereço("abc");
        cliente.setNome("Teste");
        cliente.setUltimoOrcamento("22/07/2024");
        return cliente;
    }
}
