package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetUsuarios;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuariosRepository extends CrudRepository<SetUsuarios, Integer>
        , JpaSpecificationExecutor<SetUsuarios> {

    @Query(value = "SELECT * FROM tb_usuarios where ativo = 'S'", nativeQuery = true)
    List<SetUsuarios> listAll();

    @Query(value = "SELECT * FROM tb_usuarios where nome_usuario = ?1 limit 1", nativeQuery = true)
    SetUsuarios findByusername(String username);
}
