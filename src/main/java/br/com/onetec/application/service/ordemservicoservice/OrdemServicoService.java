package br.com.onetec.application.service.ordemservicoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.model.SetOrdemServico;
import br.com.onetec.infra.db.repository.ISetOrcamentoRepository;
import br.com.onetec.infra.db.repository.ISetOrdemServicoRepository;
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
public class OrdemServicoService {

    private ISetOrdemServicoRepository repository;

    @Autowired
    public void initServices (ISetOrdemServicoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetOrdemServico> list(Pageable pageable, Specification<SetOrdemServico> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrdemServico> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public List<SetOrdemServico> findAllByOrcamentoId(Integer orcamentoid){

        return repository.listAllByOrcamentoId(orcamentoid);
    }

    public List<SetOrdemServico> findAll(){

        return repository.listAll();
    }

    public void save(SetOrdemServico dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetOrdemServico item) throws Exception {
        try {
            Optional<SetOrdemServico> optional = repository.findById(item.getId_ordemservico());
            SetOrdemServico entity = optional.get();
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
