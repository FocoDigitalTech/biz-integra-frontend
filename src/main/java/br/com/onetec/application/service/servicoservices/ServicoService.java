package br.com.onetec.application.service.servicoservices;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetServico;
import br.com.onetec.infra.db.model.SetServicosOrcamento;
import br.com.onetec.infra.db.model.SetSetorAtuacao;
import br.com.onetec.infra.db.repository.ISetServicoRepository;
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
public class ServicoService {

    private ISetServicoRepository repository;

    @Autowired
    public void initServices (ISetServicoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetServico> list(Pageable pageable, Specification<SetServico> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetServico> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetServico dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetServico item) throws Exception {
        try {
            Optional<SetServico> optional = repository.findById(item.getId_servico());
            SetServico entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetServico> listAll() {
        return repository.listAll();
    }
}