package br.com.onetec.application.service.regiaoservice;

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

import java.util.List;

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
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }


    public List<SetRegiao> findAllRegiao() {
        return repository.listAll();
    }
}
