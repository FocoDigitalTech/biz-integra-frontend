package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.model.SetFuncionario;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetFornecedorRepository extends CrudRepository<SetFornecedor, Integer>
        , JpaSpecificationExecutor<SetFornecedor> {

    @Query(value = "SELECT * FROM tb_fornecedores where ativo = 'S'", nativeQuery = true)
    List<SetFornecedor> listAll();
}
