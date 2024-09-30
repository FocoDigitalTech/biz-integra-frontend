package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartamentoRepository extends CrudRepository<SetDepartamento, Integer>
        , JpaSpecificationExecutor<SetDepartamento>{

    List<SetDepartamento> findAll();
}
