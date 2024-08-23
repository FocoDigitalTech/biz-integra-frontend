package br.com.onetec.application.service.userservice;

import br.com.onetec.infra.db.model.SetUsuarios;
import br.com.onetec.infra.db.repository.ITipoMidiaRepository;
import br.com.onetec.infra.db.repository.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {


    private IUsuariosRepository repository;

    @Autowired
    public void initServices (IUsuariosRepository repository1){
        this.repository = repository1;
    }

    public SetUsuarios findById(Integer id_usuario) {
        Optional<SetUsuarios> optionalSetUsuarios = repository.findById(id_usuario);
        return optionalSetUsuarios.get();
    }
}
