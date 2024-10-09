package br.com.onetec.application.service.userservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.views.main.configuracoessistema.div.PragasDiv;
import br.com.onetec.infra.db.model.SetGrupoUsuario;
import br.com.onetec.infra.db.model.SetTipoPagamento;
import br.com.onetec.infra.db.model.SetUsuarios;
import br.com.onetec.infra.db.repository.ITipoMidiaRepository;
import br.com.onetec.infra.db.repository.IUsuariosRepository;
import com.github.javaparser.ast.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import br.com.onetec.application.security.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
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

    public Page<SetUsuarios> list(Pageable pageable, Specification<SetUsuarios> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetUsuarios> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void delete(SetUsuarios item) throws Exception {
        try {
            Optional<SetUsuarios> optional = repository.findById(item.getId_usuario());
            SetUsuarios entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void save(SetUsuarios dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public boolean checkUserNameAvaliable(String username) {
        SetUsuarios existsUsername = repository.findByusername(username);
        if (existsUsername == null){
            return true;
        } else {
            return false;
        }
    }
}
