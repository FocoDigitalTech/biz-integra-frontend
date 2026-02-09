package br.com.onetec.application.service.tipopagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetTipoPagamento;
import br.com.onetec.infra.db.repository.ISetTipoPagamentoRepository;
import lombok.SneakyThrows;
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
    public void initServices(ISetTipoPagamentoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetTipoPagamento> list(Pageable pageable, Specification<SetTipoPagamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTipoPagamento> page = repository.findAll(filter, pageable);
        Specification<SetTipoPagamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetTipoPagamento> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetTipoPagamento dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SetTipoPagamento> listAll() {
        return repository.listAll();
    }

    public void update(SetTipoPagamento dto) throws Exception {
        try {
            Optional<SetTipoPagamento> optional = repository.findById(dto.getId_tipopagamento());
            SetTipoPagamento entity = optional.get();
            entity = dto;

            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Atualizado !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @SneakyThrows
    public SetTipoPagamento findById(Integer id_tipopagamento) {
        try {
            Optional<SetTipoPagamento> optional = repository.findById(id_tipopagamento);
            return optional.get();
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
