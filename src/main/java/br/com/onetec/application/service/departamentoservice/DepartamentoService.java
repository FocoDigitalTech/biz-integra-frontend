package br.com.onetec.application.service.departamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Departamento;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.main.administrativo.div.FuncionarioDiv;
import br.com.onetec.application.views.main.administrativo.modal.DepartamentoCadastroModal;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.repository.IDepartamentoRepository;
import br.com.onetec.infra.db.repository.IFuncionarioRepository;
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
public class DepartamentoService {

    private  IDepartamentoRepository repository;

    private  IFuncionarioRepository repositoryFuncionario;

    @Autowired
    public void initServices(IDepartamentoRepository repository1,
                             IFuncionarioRepository repositoryFuncionario1) {
        this.repository = repository1;
        this.repositoryFuncionario = repositoryFuncionario1;
    }


    public void cadastrar(Departamento dto) {
        log.info("tentando cadastrar ....");
        SetDepartamento entity = new SetDepartamento();
        entity.setAtivo("S");
        entity.setDescricao_departamento(dto.getDescricao());
        entity.setId_funcionario(dto.getResponsavel());
        entity.setData_inclusao(LocalDateTime.now());
        entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        repository.save(entity);
    }

    public Page<SetDepartamento> list(Pageable pageable, Specification<SetDepartamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetDepartamento> page = repository.findAll(filter, pageable);

        return repository.findAll(filter, pageable);
    }

    public void atualizar(Departamento dto) {

    }

    public void deletar(SetDepartamento departamento) {
        repository.delete(departamento);
    }

    public SetDepartamento findById(Integer id_departamento) {
        if (id_departamento != null) {
            Optional<SetDepartamento> optionalEntity = repository.findById(id_departamento);
            return optionalEntity.get();
        } else {
            return null;
        }
    }

    public List<SetFuncionario> findAllFuncionarios() {
        return repositoryFuncionario.findAll();
    }

    public List<SetDepartamento> findAllDepartamento() {

        return repository.findAll();
    }
}
