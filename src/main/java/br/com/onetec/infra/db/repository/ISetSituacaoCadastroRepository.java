package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISetSituacaoCadastroRepository extends CrudRepository<SetSituacaoCadastro, Integer>
        , JpaSpecificationExecutor<SetSituacaoCadastro> {
}
