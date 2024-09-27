package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetGrupoUsuario;
import br.com.onetec.infra.db.model.SetPermissao;
import br.com.onetec.infra.db.model.SetTecnicoAssistente;
import br.com.onetec.infra.db.model.SetUsuarios;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface ISetGrupoUsuarioRepository  extends CrudRepository<SetGrupoUsuario, Integer>
            , JpaSpecificationExecutor<SetGrupoUsuario> {

        @Query(value = "SELECT * FROM tb_grupousuario where ativo = 'S'", nativeQuery = true)
        List<SetGrupoUsuario> listAll();
}
