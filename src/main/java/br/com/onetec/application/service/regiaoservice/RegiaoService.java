package br.com.onetec.application.service.regiaoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetProduto;
import br.com.onetec.infra.db.model.SetRegiao;
import br.com.onetec.infra.db.model.SetTipoImovel;
import br.com.onetec.infra.db.repository.IRegiaoRepository;
import br.com.onetec.infra.db.repository.ITipoImovelRepository;
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
public class RegiaoService {

    private IRegiaoRepository repository;

    @Autowired
    public void initServices (IRegiaoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetRegiao> list(Pageable pageable, Specification<SetRegiao> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetRegiao> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetRegiao dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetRegiao item) throws Exception {
        try {
            Optional<SetRegiao> optional = repository.findById(item.getId_regiao());
            SetRegiao entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }


    public List<SetRegiao> findAllRegiao() {
        return repository.listAll();
    }
}
