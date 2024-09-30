package br.com.onetec.application.service.clientesservice;

import br.com.onetec.infra.db.model.SetEstado;
import br.com.onetec.infra.db.repository.IEstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    IEstadoRepository repository;

    public List<SetEstado> listAll() {
        return repository.findAll();
    }

    public SetEstado findById(Integer id_estado) {
        return repository.findById(id_estado).get();
    }
}
