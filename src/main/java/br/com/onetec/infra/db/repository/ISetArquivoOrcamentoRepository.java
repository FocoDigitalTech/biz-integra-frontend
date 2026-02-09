package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetArquivoOrcamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetArquivoOrcamentoRepository extends CrudRepository<SetArquivoOrcamento, Integer>
        , JpaSpecificationExecutor<SetArquivoOrcamento> {

    @Query(value = "SELECT * FROM tb_arquivosorcamento where id_orcamento = ?1", nativeQuery = true)
    List<SetArquivoOrcamento> findAllByOrcamento(Integer id_orcamento);

    @Query(value = "SELECT * FROM tb_arquivosorcamento where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetArquivoOrcamento> listAllByOrcamentoId(Integer id_orcamento);
}
