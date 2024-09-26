package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetResponsavelCobranca;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISetCondicaoPagamentoRepository extends CrudRepository<SetCondicaoPagamento, Integer>
        , JpaSpecificationExecutor<SetCondicaoPagamento> {
}
