package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetEstado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEstadoRepository extends CrudRepository<SetEstado, Integer>
        , JpaSpecificationExecutor<SetEstado> {

    List<SetEstado> findAll();
}
