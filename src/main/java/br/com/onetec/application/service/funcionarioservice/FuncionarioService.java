package br.com.onetec.application.service.funcionarioservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.views.main.administrativo.AdministrativoView;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.repository.IFuncionarioRepository;
import com.github.javaparser.ast.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FuncionarioService {

    private final IFuncionarioRepository repository;


    public FuncionarioService(IFuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<SetFuncionario> listAll (){
        return repository.findAll();
    }

    public Page<SetFuncionario> list(Pageable pageable, Specification<SetFuncionario> filter) {
        log.info("Pageable: {}", pageable);
        Specification<SetFuncionario> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetFuncionario> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public SetFuncionario findById(Integer id_funcionario) {
        SetFuncionario entity = new SetFuncionario();
        if (id_funcionario != null) {
            Optional<SetFuncionario> optionalEntity = repository.findById(id_funcionario);
            entity = optionalEntity.get();
        }
        return entity;
    }

    public void save(SetFuncionario funcionario) {
        repository.save(funcionario);
    }

    public void delete(SetFuncionario item) throws Exception {
        try {
            Optional<SetFuncionario> optional = repository.findById(item.getId_funcionario());
            SetFuncionario entity = optional.get();
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
