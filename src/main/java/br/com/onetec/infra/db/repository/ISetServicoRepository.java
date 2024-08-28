package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetServico;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISetServicoRepository extends CrudRepository<SetServico, Integer>
        , JpaSpecificationExecutor<SetServico> {
}
