package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetGrupoUsuario;
import br.com.onetec.infra.db.model.SetOrcamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrcamentoRepository extends CrudRepository<SetOrcamento, Integer>
        , JpaSpecificationExecutor<SetOrcamento> {

    @Query(value = "SELECT * FROM tb_orcamento where ativo = 'S'", nativeQuery = true)
    List<SetOrcamento> listAll();

    @Query(value = "SELECT * FROM tb_orcamento where ativo = 'S' and id_cliente = ?1", nativeQuery = true)
    List<SetOrcamento> listAllByClientId(Integer idcliente);

    @Query(value = "SELECT * FROM tb_orcamento where ativo = 'S' and id_situacao = ?1", nativeQuery = true)
    List<SetOrcamento> findAllBySituacaoId(Integer id_situacaocadastro);

    @Query(value = "SELECT max(id_orcamento) FROM tb_orcamento", nativeQuery = true)
    Integer findAllMaxId();
}
