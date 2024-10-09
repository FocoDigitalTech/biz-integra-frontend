package br.com.onetec.application.service.grupousuarioservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
import br.com.onetec.infra.db.model.SetGrupoUsuario;
import br.com.onetec.infra.db.repository.ISetGrupoUsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GrupoUsuarioService {

    private ISetGrupoUsuarioRepository repository;

    @Autowired
    public void initServices (ISetGrupoUsuarioRepository repository1){
        this.repository = repository1;
    }

    public Page<SetGrupoUsuario> list(Pageable pageable, Specification<SetGrupoUsuario> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetGrupoUsuario> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public SetGrupoUsuario findById (Integer idGrupoUsuario){
        Optional<SetGrupoUsuario> optionalSetGrupoUsuario = repository.findById(idGrupoUsuario);
        return optionalSetGrupoUsuario.get();
    }

    public List<SetGrupoUsuario> listAll() {
        return repository.listAll();
    }

    public void delete(SetGrupoUsuario item) throws Exception {
        try {
            Optional<SetGrupoUsuario> optional = repository.findById(item.getId_grupousuario());
            SetGrupoUsuario entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public SetGrupoUsuario save(SetGrupoUsuario dto) throws Exception {
        try {
            return repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }
}
