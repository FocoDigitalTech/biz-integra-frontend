package br.com.onetec.application.service.clientesservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Cliente;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetUsuarios;
import br.com.onetec.infra.db.repository.IClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientesService {

    @Autowired
    IClienteRepository repository;

    public Page<SetCliente> list(Pageable pageable, Specification<SetCliente> filter) {
        Specification<SetCliente> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetCliente> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }


    public SetCliente save(Cliente dto) {
        SetCliente entity = new SetCliente();
        entity.setAtivo("S");
        entity.setNome_cliente(dto.getNomeField());
        entity.setNome_fantasia_cliente(dto.getNomeField());
        entity.setTelefone_cliente(dto.getTelefoneField());
        entity.setEmail_cliente(dto.getInternetEmailField());
        entity.setNome_contato_cliente(dto.getContatoField());
        entity.setCargo_contato_cliente("");
        entity.setId_anuncio(dto.getTipoMidia());
        entity.setId_indicacao(1);
        entity.setCpf_cgc_cliente(dto.getCGCCPFField());
        entity.setIest_cliente(dto.getInscEstatualField());
        entity.setTipo_naturezajuridica(dto.getFJField());
        entity.setNumero_naturezajuridica(dto.getCnpjField());
        entity.setObservacoes_cliente(dto.getObservacaoField());
        entity.setMarca_cliente("Marca Ltda");
        entity.setAdministradora_cliente("Concecionaria");
        entity.setCelular_cliente(dto.getCelularField());
        entity.setHora_ligacao_cliente(dto.getHoraField());
        entity.setId_tipo_imovel(1);
        entity.setEndereco_cliente("Rua 1");
        entity.setNumero_res_cliente("655");
        entity.setComplemento_cliente("Complemento");
        entity.setBairro_cliente("Teste");
        entity.setCep_cliente("000000");
        entity.setCidade_cliente("Teste");

        entity.setId_estado(1);
        entity.setResponsavel_cliente("Carlos");
        entity.setPonto_referencia_cliente("Teste");
        entity.setData_inclusao(LocalDateTime.now());
        entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        log.info("Salvando novo cliente :" + entity.toString());
        repository.save(entity);
        return entity;
    }

    public SetCliente findById(Integer idCliente) {
        Optional<SetCliente> optional = repository.findById(idCliente);
        return optional.orElse(null);
    }

    public void logicalDelete(SetCliente cliente, SetUsuarios user) throws Exception {
        Optional<SetCliente> optional = repository.findById(cliente.getId_cliente());
        SetCliente setCliente = optional.orElse(null);
        try {
            assert setCliente != null;
            setCliente.setData_exclusao(LocalDateTime.now());
            setCliente.setAtivo("N");
            setCliente.setId_usuario(user.getId_usuario());
          repository.save(setCliente);
          log.info("Cliente excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetCliente update(Cliente dto) {
        Optional<SetCliente> optional = repository.findById(dto.getId_cliente());
        SetCliente entity = optional.get();
        entity.setAtivo("S");
        entity.setNome_cliente(dto.getNomeField());
        entity.setNome_fantasia_cliente(dto.getNomeField());
        entity.setTelefone_cliente(dto.getTelefoneField());
        entity.setEmail_cliente(dto.getInternetEmailField());
        entity.setNome_contato_cliente(dto.getContatoField());
        entity.setCargo_contato_cliente("");
        entity.setId_anuncio(dto.getTipoMidia());
        entity.setId_indicacao(1);
        entity.setCpf_cgc_cliente(dto.getCGCCPFField());
        entity.setIest_cliente(dto.getInscEstatualField());
        entity.setTipo_naturezajuridica(dto.getFJField());
        entity.setNumero_naturezajuridica(dto.getCnpjField());
        entity.setObservacoes_cliente(dto.getObservacaoField());
        entity.setAdministradora_cliente("Concecionaria");
        entity.setCelular_cliente(dto.getCelularField());
        entity.setHora_ligacao_cliente(dto.getHoraField());
        entity.setId_tipo_imovel(1);
        entity.setId_estado(1);
        entity.setResponsavel_cliente(dto.getContatoField());
        entity.setData_alteracao(LocalDateTime.now());
        entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        log.info("Salvando novo cliente :" + entity.toString());
        repository.save(entity);
        return entity;
    }
}
