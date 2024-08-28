package br.com.onetec.application.service.tipoatendimentoservice;

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
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

}
