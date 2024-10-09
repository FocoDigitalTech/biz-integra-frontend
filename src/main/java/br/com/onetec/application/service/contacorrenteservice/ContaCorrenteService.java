package br.com.onetec.application.service.contacorrenteservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetContaCorrente;
import br.com.onetec.infra.db.repository.ISetCondicaoPagamentoRepository;
import br.com.onetec.infra.db.repository.ISetContaCorrenteRepository;
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
public class ContaCorrenteService {

    private ISetContaCorrenteRepository repository;


    @Autowired
    public void initServices(ISetContaCorrenteRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetContaCorrente> list(Pageable pageable, Specification<SetContaCorrente> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetContaCorrente> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetContaCorrente dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetContaCorrente item) throws Exception {
        try {
            Optional<SetContaCorrente> optional = repository.findById(item.getId_contacorrente());
            SetContaCorrente entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Cliente excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetContaCorrente> findAll() {
        return repository.listAll();
    }
}
