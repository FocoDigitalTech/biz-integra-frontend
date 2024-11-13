package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrcamentoContato;
import br.com.onetec.infra.db.model.SetOrcamentoPosVenda;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrcamentoPosVendaRepository extends CrudRepository<SetOrcamentoPosVenda, Integer>
        , JpaSpecificationExecutor<SetOrcamentoPosVenda> {

    @Query(value = "SELECT * FROM tb_orcamentoposvenda where ativo = 'S'", nativeQuery = true)
    List<SetOrcamentoPosVenda> listAll();

    @Query(value = "SELECT * FROM tb_orcamentoposvenda where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetOrcamentoPosVenda> listAllByOrcamentoId(Integer id_orcamento);
}
