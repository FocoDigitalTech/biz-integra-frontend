package br.com.onetec.application.service.departamentoservice;

import br.com.onetec.application.model.Departamento;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.repository.IDepartamentoRepository;
import br.com.onetec.infra.db.repository.IFuncionarioRepository;
import lombok.extern.slf4j.Slf4j;
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

    private final IDepartamentoRepository repository;

    private final IFuncionarioRepository repositoryFuncionario;

    public DepartamentoService(IDepartamentoRepository repository, IFuncionarioRepository repositoryFuncionario) {
        this.repository = repository;
        this.repositoryFuncionario = repositoryFuncionario;
    }

    public void cadastrar(Departamento dto) {
        log.info("tentando cadastrar ....");
        SetDepartamento entity = new SetDepartamento();
        entity.setAtivo("S");
        entity.setDescricao_departamento(dto.getDescricao());
        entity.setId_funcionario(dto.getResponsavel());
        entity.setData_inclusao(LocalDateTime.now());
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
        Optional<SetDepartamento> optionalEntity = repository.findById(id_departamento);
        return optionalEntity.get();
    }

    public List<SetFuncionario> findAllFuncionarios() {
        return repositoryFuncionario.findAll();
    }
}
