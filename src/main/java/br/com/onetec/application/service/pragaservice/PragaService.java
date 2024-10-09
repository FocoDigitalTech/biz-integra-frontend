package br.com.onetec.application.service.pragaservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetPermissao;
import br.com.onetec.infra.db.model.SetPraga;
import br.com.onetec.infra.db.model.SetRegiao;
import br.com.onetec.infra.db.repository.IRegiaoRepository;
import br.com.onetec.infra.db.repository.ISetPragaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PragaService {

    private ISetPragaRepository repository;

    @Autowired
    public void initServices (ISetPragaRepository repository1){
        this.repository = repository1;
    }

    public Page<SetPraga> list(Pageable pageable, Specification<SetPraga> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetPraga> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetPraga dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetPraga item) throws Exception {
        try {
            Optional<SetPraga> optional = repository.findById(item.getId_praga());
            SetPraga entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

}