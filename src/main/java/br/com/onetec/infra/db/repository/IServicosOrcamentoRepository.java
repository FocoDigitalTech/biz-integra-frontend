package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetServicosOrcamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IServicosOrcamentoRepository extends CrudRepository<SetServicosOrcamento, Integer>
        , JpaSpecificationExecutor<SetServicosOrcamento> {

    @Query(value = "SELECT * FROM tb_servicosorcamento where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetServicosOrcamento> findByOrcamento(Integer id_orcamento);
}
