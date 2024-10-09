package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetFaturamento;
import br.com.onetec.infra.db.model.SetFornecedorContato;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetFornecedorContatoRepository extends CrudRepository<SetFornecedorContato, Integer>
        , JpaSpecificationExecutor<SetFornecedorContato> {

    @Query(value = "SELECT * FROM tb_fornecedorcontato where ativo = 'S'", nativeQuery = true)
    List<SetFornecedorContato> listAll();
}
