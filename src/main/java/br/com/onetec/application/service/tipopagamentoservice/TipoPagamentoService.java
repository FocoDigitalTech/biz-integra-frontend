package br.com.onetec.application.service.tipopagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetSituacaoPagamento;
import br.com.onetec.infra.db.model.SetTipoMidia;
import br.com.onetec.infra.db.model.SetTipoPagamento;
import br.com.onetec.infra.db.repository.ISetSituacaoPagamentoRepository;
import br.com.onetec.infra.db.repository.ISetTipoPagamentoRepository;
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
public class TipoPagamentoService {

    private ISetTipoPagamentoRepository repository;

    @Autowired
    public void initServices (ISetTipoPagamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetTipoPagamento> list(Pageable pageable, Specification<SetTipoPagamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTipoPagamento> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetTipoPagamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetTipoPagamento item) throws Exception {
        try {
            Optional<SetTipoPagamento> optional = repository.findById(item.getId_tipopagamento());
            SetTipoPagamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetTipoPagamento> listAll() {
        return repository.listAll();
    }
}
