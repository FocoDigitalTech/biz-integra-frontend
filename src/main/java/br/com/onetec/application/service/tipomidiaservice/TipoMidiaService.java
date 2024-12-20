package br.com.onetec.application.service.tipomidiaservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetTipoMidia;
import br.com.onetec.infra.db.repository.ITipoMidiaRepository;
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
public class TipoMidiaService {



    private ITipoMidiaRepository repository;

    @Autowired
    public void initServices (ITipoMidiaRepository repository1){
        this.repository = repository1;
    }

    public Page<SetTipoMidia> list(Pageable pageable, Specification<SetTipoMidia> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTipoMidia> page = repository.findAll(filter, pageable);
        Specification<SetTipoMidia> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetTipoMidia> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetTipoMidia dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetTipoMidia item) throws Exception {
        try {
            Optional<SetTipoMidia> optional = repository.findById(item.getId_tipomidia());
            SetTipoMidia entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetTipoMidia> findAllMidia() {
        return repository.listAll();
    }

    public SetTipoMidia findByIdMidia(Integer id_anuncio) {
        return repository.findById(id_anuncio).orElse(null);
    }
}
