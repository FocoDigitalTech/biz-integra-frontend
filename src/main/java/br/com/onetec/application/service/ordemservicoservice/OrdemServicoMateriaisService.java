package br.com.onetec.application.service.ordemservicoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrdemServicoMateriais;
import br.com.onetec.infra.db.model.SetProduto;
import br.com.onetec.infra.db.repository.ISetOrdemServicoMateriaisRepository;
import br.com.onetec.infra.db.repository.ISetProdutoRepository;
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
public class OrdemServicoMateriaisService {

    private ISetOrdemServicoMateriaisRepository repository;

    private ISetProdutoRepository iSetEstoqueRepository;

    @Autowired
    public void initServices(ISetOrdemServicoMateriaisRepository repository1, ISetProdutoRepository iSetEstoqueRepository1) {
        this.repository = repository1;
        this.iSetEstoqueRepository = iSetEstoqueRepository1;
    }

    public Page<SetOrdemServicoMateriais> list(Pageable pageable, Specification<SetOrdemServicoMateriais> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrdemServicoMateriais> page = repository.findAll(filter, pageable);
        Specification<SetOrdemServicoMateriais> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrdemServicoMateriais> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public List<SetOrdemServicoMateriais> listAllByOrdemServicoId(Integer id) {

        return repository.listAllByOrdemServicoId(id);
    }

    public void save(SetOrdemServicoMateriais dto) throws Exception {
        try {
            repository.save(dto);
            atualizaEstoque(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }


    @SneakyThrows
    private void atualizaEstoque(SetOrdemServicoMateriais dto) {
        try {
            Optional<SetProduto> optional = iSetEstoqueRepository.findById(dto.getId_produto());
            SetProduto entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
//            var saldo = Integer.parseInt(entity.getQuantidade_estoque())
//                    - dto.getQuantidadeconsumida_ordemservicomateriais();
//            entity.setQuantidade_estoque(String.valueOf(saldo));
            iSetEstoqueRepository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetOrdemServicoMateriais item) throws Exception {
        try {
            Optional<SetOrdemServicoMateriais> optional = repository.findById(item.getId_ordemservico());
            SetOrdemServicoMateriais entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetOrdemServicoMateriais findById(Integer value) {
        Optional<SetOrdemServicoMateriais> optional = repository.findById(value);
        return optional.orElse(null);
    }

    public void update(SetOrdemServicoMateriais p) throws Exception {
        try {
            Optional<SetOrdemServicoMateriais> optional = repository.findById(p.getId_ordemservico());
            SetOrdemServicoMateriais entity = optional.get();
            entity = p;
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
