package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetControleProduto;
import br.com.onetec.infra.db.model.SetProduto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISetControleProdutoRepository extends CrudRepository<SetControleProduto, Integer>
        , JpaSpecificationExecutor<SetControleProduto> {
}
