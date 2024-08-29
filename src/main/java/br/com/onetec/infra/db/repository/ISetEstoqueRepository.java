package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetEstoque;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISetEstoqueRepository extends CrudRepository<SetEstoque, Integer>
        , JpaSpecificationExecutor<SetEstoque> {
}
