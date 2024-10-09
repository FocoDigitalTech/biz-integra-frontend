package br.com.onetec.application.service.tipoimovelservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
import br.com.onetec.infra.db.model.SetTipoImovel;
import br.com.onetec.infra.db.model.SetTipoMidia;
import br.com.onetec.infra.db.repository.ITipoImovelRepository;
import br.com.onetec.infra.db.repository.ITipoMidiaRepository;
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
public class TipoImovelService {

    private ITipoImovelRepository repository;

    @Autowired
    public void initServices (ITipoImovelRepository repository1){
        this.repository = repository1;
    }

    public Page<SetTipoImovel> list(Pageable pageable, Specification<SetTipoImovel> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTipoImovel> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetTipoImovel dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetTipoImovel item) throws Exception {
        try {
            Optional<SetTipoImovel> optional = repository.findById(item.getId_tipoimovel());
            SetTipoImovel entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetTipoImovel> findAllImovel() {
        return repository.listAll();
    }


}
