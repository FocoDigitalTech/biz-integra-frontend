package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrcamentoContato;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrcamentoContatoRepository extends CrudRepository<SetOrcamentoContato, Integer>
        , JpaSpecificationExecutor<SetOrcamentoContato> {

    @Query(value = "SELECT * FROM tb_orcamentocontato where ativo = 'S'", nativeQuery = true)
    List<SetOrcamentoContato> listAll();

    @Query(value = "SELECT * FROM tb_orcamentocontato where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetOrcamentoContato> listAllByOrcamentoId(Integer id_orcamento);
}
