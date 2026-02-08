package br.com.onetec.application.service.veiculoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetVeiculo;
import br.com.onetec.infra.db.repository.ISetVeiculoRepository;
import lombok.SneakyThrows;
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
public class VeiculoService {

    private ISetVeiculoRepository repository;

    @Autowired
    public void initServices(ISetVeiculoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetVeiculo> list(Pageable pageable, Specification<SetVeiculo> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetVeiculo> page = repository.findAll(filter, pageable);
        Specification<SetVeiculo> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetVeiculo> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetVeiculo dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @SneakyThrows
    public void delete(SetVeiculo item) {
        try {
            Optional<SetVeiculo> optional = repository.findById(item.getId_veiculo());
            SetVeiculo entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void update(SetVeiculo item) throws Exception {
        try {
            Optional<SetVeiculo> optional = repository.findById(item.getId_veiculo());
            SetVeiculo entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
