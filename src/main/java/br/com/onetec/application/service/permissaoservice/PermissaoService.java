package br.com.onetec.application.service.permissaoservice;

import br.com.onetec.infra.db.model.SetGrupoUsuario;
import br.com.onetec.infra.db.model.SetPermissao;
import br.com.onetec.infra.db.repository.ISetGrupoUsuarioRepository;
import br.com.onetec.infra.db.repository.ISetPermissaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PermissaoService {

    private ISetPermissaoRepository repository;

    @Autowired
    public void initServices (ISetPermissaoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetPermissao> list(Pageable pageable, Specification<SetPermissao> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetPermissao> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public SetPermissao findById (Integer idGrupoUsuario){
        Optional<SetPermissao> optionalSetGrupoUsuario = repository.findById(idGrupoUsuario);
        return optionalSetGrupoUsuario.get();
    }

    public void delete(SetPermissao item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void save(SetPermissao dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void saveAll(List<SetPermissao> listaPermissao) throws Exception {
        try {
            listaPermissao.forEach(dto -> {
                repository.save(dto);
            });
        }catch (Exception e){
            throw new Exception();
        }
    }
}
