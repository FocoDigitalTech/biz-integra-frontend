package br.com.onetec.application.service.tipomidiaservice;

import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.model.SetTipoMidia;
import br.com.onetec.infra.db.repository.ITipoMidiaRepository;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TipoMidiaService {



    private ITipoMidiaRepository repository;

    @Autowired
    public void initServices (ITipoMidiaRepository repository1){
        this.repository = repository1;
    }

    public Page<SetTipoMidia> list(Pageable pageable, Specification<SetTipoMidia> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTipoMidia> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetTipoMidia dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetTipoMidia item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetTipoMidia> findAllMidia() {
        return repository.listAll();
    }
}
