package br.com.onetec.application.service.faturamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetFaturamento;
import br.com.onetec.infra.db.model.SetVeiculo;
import br.com.onetec.infra.db.repository.ISetFaturamentoRepository;
import br.com.onetec.infra.db.repository.ISetVeiculoRepository;
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
public class FaturamentoService {

    private ISetFaturamentoRepository repository;

    @Autowired
    public void initServices (ISetFaturamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetFaturamento> list(Pageable pageable, Specification<SetFaturamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetFaturamento> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetFaturamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetFaturamento item) throws Exception {
        try {
            Optional<SetFaturamento> optional = repository.findById(item.getId_faturamento());
            SetFaturamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void update(SetFaturamento item) throws Exception {
        try {
            Optional<SetFaturamento> optional = repository.findById(item.getId_faturamento());
            SetFaturamento entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
