package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetTipoImovel;
import br.com.onetec.infra.db.model.SetTipoPagamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetTipoPagamentoRepository extends CrudRepository<SetTipoPagamento, Integer>
        , JpaSpecificationExecutor<SetTipoPagamento> {

    @Query(value = "SELECT * FROM tb_tipopagamento where ativo = 'S'", nativeQuery = true)
    List<SetTipoPagamento> listAll();
}
