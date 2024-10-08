package br.com.onetec.application.service.tecnicoassistenteservice;

import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import br.com.onetec.infra.db.model.SetTecnicoAssistente;
import br.com.onetec.infra.db.repository.ISetSituacaoCadastroRepository;
import br.com.onetec.infra.db.repository.ISetTecnicoAssistenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TecnicoAssistenteService {

    private ISetTecnicoAssistenteRepository repository;

    @Autowired
    public void initServices (ISetTecnicoAssistenteRepository repository1){
        this.repository = repository1;
    }

    public Page<SetTecnicoAssistente> list(Pageable pageable, Specification<SetTecnicoAssistente> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTecnicoAssistente> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetTecnicoAssistente dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetTecnicoAssistente item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetTecnicoAssistente> findAll() {
        return repository.listAll();
    }
}