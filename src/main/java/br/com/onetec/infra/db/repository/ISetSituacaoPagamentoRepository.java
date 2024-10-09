package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import br.com.onetec.infra.db.model.SetSituacaoPagamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ISetSituacaoPagamentoRepository extends CrudRepository<SetSituacaoPagamento, Integer>
        , JpaSpecificationExecutor<SetSituacaoPagamento> {

    @Query(value = "SELECT * FROM tb_situacaopagamento where ativo = 'S'", nativeQuery = true)
    List<SetSituacaoPagamento> listAll();
}