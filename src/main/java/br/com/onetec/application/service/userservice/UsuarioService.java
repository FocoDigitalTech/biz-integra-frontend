package br.com.onetec.application.service.userservice;

import br.com.onetec.infra.db.repository.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    IUsuariosRepository repository;
}
