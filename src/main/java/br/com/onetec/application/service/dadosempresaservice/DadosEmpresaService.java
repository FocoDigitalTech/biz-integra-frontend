package br.com.onetec.application.service.dadosempresaservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetDadosEmpresa;
import br.com.onetec.infra.db.repository.ISetDadosEmpresaRepository;
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
public class DadosEmpresaService {

    private ISetDadosEmpresaRepository repository;


    @Autowired
    public void initServices(ISetDadosEmpresaRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetDadosEmpresa> list(Pageable pageable, Specification<SetDadosEmpresa> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetDadosEmpresa> page = repository.findAll(filter, pageable);
        Specification<SetDadosEmpresa> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetDadosEmpresa> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetDadosEmpresa dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetDadosEmpresa item) throws Exception {
        try {
            Optional<SetDadosEmpresa> optional = repository.findById(item.getId_dadosempresa());
            SetDadosEmpresa entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void update(SetDadosEmpresa item) throws Exception {
        try {
            Optional<SetDadosEmpresa> optional = repository.findById(item.getId_dadosempresa());
            SetDadosEmpresa entity = optional.get();
            entity = item;
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetDadosEmpresa findById(Integer idcontrato) {
        Optional<SetDadosEmpresa> optional = repository.findById(idcontrato);
        return optional.orElse(null);
    }

    public SetDadosEmpresa getDados(Integer id_orcamento) {
        return repository.findActive();
    }
}
