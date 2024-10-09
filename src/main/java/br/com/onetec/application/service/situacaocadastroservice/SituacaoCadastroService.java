package br.com.onetec.application.service.situacaocadastroservice;


import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetSetorAtuacao;
import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import br.com.onetec.infra.db.model.SetTipoAtendimento;
import br.com.onetec.infra.db.repository.ISetSituacaoCadastroRepository;
import br.com.onetec.infra.db.repository.ISetTipoAtendimentoRepository;
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
public class SituacaoCadastroService {

    private ISetSituacaoCadastroRepository repository;

    @Autowired
    public void initServices (ISetSituacaoCadastroRepository repository1){
        this.repository = repository1;
    }

    public Page<SetSituacaoCadastro> list(Pageable pageable, Specification<SetSituacaoCadastro> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetSituacaoCadastro> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetSituacaoCadastro dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetSituacaoCadastro item) throws Exception {
        try {
            Optional<SetSituacaoCadastro> optional = repository.findById(item.getId_situacaocadastro());
            SetSituacaoCadastro entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetSituacaoCadastro> listAll() {
        return repository.listAll();
    }
}
