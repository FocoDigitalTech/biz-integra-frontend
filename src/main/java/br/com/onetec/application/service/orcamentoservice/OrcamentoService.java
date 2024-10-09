package br.com.onetec.application.service.orcamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetFluxoRecebimentoPagamento;
import br.com.onetec.infra.db.model.SetNotaFiscal;
import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.repository.ISetFuxoRecebimentoPagamentoRepository;
import br.com.onetec.infra.db.repository.ISetOrcamentoRepository;
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
public class OrcamentoService {

    private ISetOrcamentoRepository repository;

    @Autowired
    public void initServices (ISetOrcamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetOrcamento> list(Pageable pageable, Specification<SetOrcamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrcamento> page = repository.findAll(filter, pageable);
        Specification<SetOrcamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");

        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrcamento> filtroComCondicao = filter.and(novaCondicao);

        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public Page<SetOrcamento> listByCustomer(Pageable pageable, Specification<SetOrcamento> filter, SetCliente entidade) {
        log.info("Pageable: {}", pageable);
        Page<SetOrcamento> page = repository.findAll(filter, pageable);
        Specification<SetOrcamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id_cliente"), entidade.getId_cliente());
        Specification<SetOrcamento> filtroComCondicao = filter.and(novaCondicao);
        Specification<SetOrcamento> novaCondicaoAtivo = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        Specification<SetOrcamento> filtroComCondicaoAtivo = filtroComCondicao.and(novaCondicaoAtivo);
        return repository.findAll(filtroComCondicaoAtivo, pageable);
    }

    public SetOrcamento save(SetOrcamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
        return dto;
    }

    public void delete(SetOrcamento item) throws Exception {
        try {
            Optional<SetOrcamento> optional = repository.findById(item.getId_orcamento());
            SetOrcamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetOrcamento> findAllClienteId(Integer id_cliente) {
        return repository.listAllByClientId(id_cliente);
    }

    public SetOrcamento findAllById(Integer id_orcamento) {
        return repository.findById(id_orcamento).orElse(null);
    }
}
