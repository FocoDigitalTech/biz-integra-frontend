package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetFuncionario;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFuncionarioRepository extends CrudRepository<SetFuncionario, Integer>
        , JpaSpecificationExecutor<SetFuncionario> {

    List<SetFuncionario> findAll();

}
