package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.model.SetFuncionario;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFornecedorRepository extends CrudRepository<SetFornecedor, Integer>
        , JpaSpecificationExecutor<SetFornecedor> {
}
