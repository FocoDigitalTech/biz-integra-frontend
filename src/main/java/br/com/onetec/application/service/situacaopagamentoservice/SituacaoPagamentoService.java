package br.com.onetec.application.service.situacaopagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import br.com.onetec.infra.db.model.SetSituacaoPagamento;
import br.com.onetec.infra.db.repository.ISetSituacaoCadastroRepository;
import br.com.onetec.infra.db.repository.ISetSituacaoPagamentoRepository;
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
public class SituacaoPagamentoService {

    private ISetSituacaoPagamentoRepository repository;

    @Autowired
    public void initServices (ISetSituacaoPagamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetSituacaoPagamento> list(Pageable pageable, Specification<SetSituacaoPagamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetSituacaoPagamento> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetSituacaoPagamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetSituacaoPagamento item) throws Exception {
        try {
            Optional<SetSituacaoPagamento> optional = repository.findById(item.getId_situacaopagamento());
            SetSituacaoPagamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetSituacaoPagamento> listAll() {
        return repository.listAll();
    }
}
