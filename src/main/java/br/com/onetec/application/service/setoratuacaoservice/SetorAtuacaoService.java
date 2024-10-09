package br.com.onetec.application.service.setoratuacaoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetServico;
import br.com.onetec.infra.db.model.SetSetorAtuacao;
import br.com.onetec.infra.db.repository.ISetSetorAtuacaoRepository;
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
public class SetorAtuacaoService {

    private ISetSetorAtuacaoRepository repository;

    @Autowired
    public void initServices (ISetSetorAtuacaoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetSetorAtuacao> list(Pageable pageable, Specification<SetSetorAtuacao> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetSetorAtuacao> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetSetorAtuacao dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetSetorAtuacao item) throws Exception {
        try {
            Optional<SetSetorAtuacao> optional = repository.findById(item.getId_setoratuacao());
            SetSetorAtuacao entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetSetorAtuacao> listAll() {
        return repository.listAll();
    }
}
