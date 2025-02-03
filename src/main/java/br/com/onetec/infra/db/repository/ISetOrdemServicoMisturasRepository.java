package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrdemServicoMateriais;
import br.com.onetec.infra.db.model.SetOrdemServicoMisturas;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrdemServicoMisturasRepository extends CrudRepository<SetOrdemServicoMisturas, Integer>
        , JpaSpecificationExecutor<SetOrdemServicoMisturas> {

    @Query(value = "SELECT * FROM tb_ordemservicomisturas where ativo = 'S' and id_ordemservico = ?1", nativeQuery = true)
    List<SetOrdemServicoMisturas> listAllByOrdemServicoId(Integer id_ordemservicomateriais);
}
