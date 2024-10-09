package br.com.onetec.application.service.tipoatendimentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetTecnicoAssistente;
import br.com.onetec.infra.db.model.SetTipoAtendimento;
import br.com.onetec.infra.db.model.SetTipoImovel;
import br.com.onetec.infra.db.repository.ISetTipoAtendimentoRepository;
import br.com.onetec.infra.db.repository.ITipoImovelRepository;
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
public class TipoAtendimentoService {

    private ISetTipoAtendimentoRepository repository;

    @Autowired
    public void initServices (ISetTipoAtendimentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetTipoAtendimento> list(Pageable pageable, Specification<SetTipoAtendimento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTipoAtendimento> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetTipoAtendimento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetTipoAtendimento item) throws Exception {
        try {
            Optional<SetTipoAtendimento> optional = repository.findById(item.getId_tipoatendimento());
            SetTipoAtendimento entity = optional.get();
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
