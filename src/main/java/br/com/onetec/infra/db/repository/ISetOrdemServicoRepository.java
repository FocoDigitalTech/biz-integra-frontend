package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.model.SetOrdemServico;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrdemServicoRepository extends CrudRepository<SetOrdemServico, Integer>
        , JpaSpecificationExecutor<SetOrdemServico> {

    @Query(value = "SELECT * FROM tb_ordemservico where ativo = 'S'", nativeQuery = true)
    List<SetOrdemServico> listAll();

    @Query(value = "SELECT * FROM tb_ordemservico where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetOrdemServico> listAllByOrcamentoId(Integer id_orcamento);
}
