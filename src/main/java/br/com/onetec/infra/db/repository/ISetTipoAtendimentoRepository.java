package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetResponsavelCobranca;
import br.com.onetec.infra.db.model.SetTipoAtendimento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISetTipoAtendimentoRepository extends CrudRepository<SetTipoAtendimento, Integer>
        , JpaSpecificationExecutor<SetTipoAtendimento> {
}
