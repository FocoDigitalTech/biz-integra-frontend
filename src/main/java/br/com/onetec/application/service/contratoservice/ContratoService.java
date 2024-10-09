package br.com.onetec.application.service.contratoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetContaCorrente;
import br.com.onetec.infra.db.model.SetContrato;
import br.com.onetec.infra.db.repository.ISetContaCorrenteRepository;
import br.com.onetec.infra.db.repository.ISetContratoRepository;
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
public class ContratoService {

    private ISetContratoRepository repository;


    @Autowired
    public void initServices(ISetContratoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetContrato> list(Pageable pageable, Specification<SetContrato> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetContrato> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetContrato dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetContrato item) throws Exception {
        try {
            Optional<SetContrato> optional = repository.findById(item.getId_contrato());
            SetContrato entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void update(SetContrato item) throws Exception {
        try {
            Optional<SetContrato> optional = repository.findById(item.getId_contrato());
            SetContrato entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
