package br.com.onetec.application.service.permissaoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetPermissao;
import br.com.onetec.infra.db.repository.ISetPermissaoRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PermissaoService {

    private ISetPermissaoRepository repository;

    @Autowired
    public void initServices (ISetPermissaoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetPermissao> list(Pageable pageable, Specification<SetPermissao> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetPermissao> page = repository.findAll(filter, pageable);
        Specification<SetPermissao> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetPermissao> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public SetPermissao findById (Integer idGrupoUsuario){
        Optional<SetPermissao> optionalSetGrupoUsuario = repository.findById(idGrupoUsuario);
        return optionalSetGrupoUsuario.get();
    }

    public List<SetPermissao> findAllById (Integer idGrupoUsuario){
        return repository.listAllById(idGrupoUsuario);
    }

    public void delete(SetPermissao item) throws Exception {
        try {
            Optional<SetPermissao> optional = repository.findById(item.getId_permissao());
            SetPermissao entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void save(SetPermissao dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void saveAll(List<SetPermissao> listaPermissao) throws Exception {
        try {
            listaPermissao.forEach(dto -> {
                repository.save(dto);
            });
        }catch (Exception e){
            throw new Exception();
        }
    }

    @SneakyThrows
    public void updateAll(List<SetPermissao> listaPermissao) {
        if (listaPermissao.size() > 0){
            for (SetPermissao item : listaPermissao){
                try {
                    Optional<SetPermissao> optional = repository.findById(item.getId_permissao());
                    SetPermissao entity = optional.get();
                    entity = item;
                    repository.save(entity);
                    log.info("Atualizado !");
                } catch (Exception e){
                    throw new Exception();
                }
            }
        }
    }
}
