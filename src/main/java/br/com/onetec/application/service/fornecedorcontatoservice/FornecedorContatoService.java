package br.com.onetec.application.service.fornecedorcontatoservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetFornecedorContato;
import br.com.onetec.infra.db.repository.ISetFornecedorContatoRepository;
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
public class FornecedorContatoService {

    private ISetFornecedorContatoRepository repository;

    @Autowired
    public void initServices(ISetFornecedorContatoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetFornecedorContato> list(Pageable pageable, Specification<SetFornecedorContato> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetFornecedorContato> page = repository.findAll(filter, pageable);
        Specification<SetFornecedorContato> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetFornecedorContato> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetFornecedorContato dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetFornecedorContato item) throws Exception {
        try {
            Optional<SetFornecedorContato> optional = repository.findById(item.getId_fornecedorcontato());
            SetFornecedorContato entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void update(SetFornecedorContato item) throws Exception {
        try {
            Optional<SetFornecedorContato> optional = repository.findById(item.getId_fornecedorcontato());
            SetFornecedorContato entity = optional.get();
            entity = item;
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SetFornecedorContato> listAllByFornecedorId(Integer id_fornecedor) {
        return repository.listAllByFornecedor(id_fornecedor);
    }
}
