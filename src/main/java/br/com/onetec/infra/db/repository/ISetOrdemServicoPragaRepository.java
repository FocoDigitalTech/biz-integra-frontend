package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrdemServicoMisturas;
import br.com.onetec.infra.db.model.SetOrdemServicoPraga;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrdemServicoPragaRepository extends CrudRepository<SetOrdemServicoPraga, Integer>
        , JpaSpecificationExecutor<SetOrdemServicoPraga> {

    @Query(value = "SELECT * FROM tb_ordemservicopraga where ativo = 'S' and id_ordemservico = ?1", nativeQuery = true)
    List<SetOrdemServicoPraga> listAllByOrdemServicoId(Integer id_ordemservicomateriais);
}

