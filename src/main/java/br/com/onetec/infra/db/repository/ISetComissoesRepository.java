package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetComissoes;
import br.com.onetec.infra.db.model.SetServicosOrcamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetComissoesRepository  extends CrudRepository<SetComissoes, Integer>
        , JpaSpecificationExecutor<SetComissoes> {

    @Query(value = "SELECT * FROM tb_comissoes where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetComissoes> findByOrcamento(Integer id_orcamento);
}