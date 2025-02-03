package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetSituacaoCadastroRepository extends CrudRepository<SetSituacaoCadastro, Integer>
        , JpaSpecificationExecutor<SetSituacaoCadastro> {

    @Query(value = "SELECT * FROM tb_situacaocadastro where ativo = 'S'", nativeQuery = true)
    List<SetSituacaoCadastro> listAll();
}
