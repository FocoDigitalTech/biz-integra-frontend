package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetNotaFiscal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetNotaFiscalRepository extends CrudRepository<SetNotaFiscal, Integer>
        , JpaSpecificationExecutor<SetNotaFiscal> {

    @Query(value = "SELECT * FROM tb_notafiscal where ativo = 'S'", nativeQuery = true)
    List<SetNotaFiscal> listAll();

    @Query(value = "SELECT * FROM tb_notafiscal where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetNotaFiscal> listAllByOrcamentoId(Integer id_orcamento);
}