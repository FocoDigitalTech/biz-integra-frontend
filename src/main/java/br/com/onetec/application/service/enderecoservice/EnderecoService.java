package br.com.onetec.application.service.enderecoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Endereco;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetEnderecos;
import br.com.onetec.infra.db.repository.IEnderecosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnderecoService {

    @Autowired
    IEnderecosRepository repository;

    public void save(List<Endereco> enderecos,Integer idCliente, Integer idUsuario) {
        enderecos.forEach(e -> {
            SetEnderecos et = new SetEnderecos();
            et.setAtivo("S");
            et.setArea_imovel(e.getFieldEnderecosArea());
            et.setBairro_imovel(e.getFieldEnderecosBairro());
            et.setCep_imovel(e.getFieldEnderecosCEP());
            et.setEndereco_imovel(e.getFieldEnderecosEndereço());
            et.setCidade_imovel(e.getFieldEnderecosCidade());
            et.setComplemento_imovel(e.getFieldEnderecosComplemento());
            et.setData_inclusao(LocalDateTime.now());
            et.setId_cliente(idCliente);
            et.setId_estado(e.getComboEnderecosUF().getId_estado());
            et.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            et.setNome_responsavel(e.getFieldEnderecosReponsavel());
            et.setNumero_imovel(e.getFieldEnderecosNumero());
            et.setPonto_referencia(e.getFieldEnderecosPontodeReferencia());
            et.setPagina_guia(e.getFieldEnderecosPagGuia());
            et.setTelefone_local(e.getFieldEnderecosTelefone());
            et.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            et.setAtivo("S");
            log.info("Salvando novo endereço :" + et.toString());
            repository.save(et);
        });
    }

    public List<SetEnderecos> findAllClienteId(Integer id_cliente) {
        List<SetEnderecos> lista = repository.findAllByCliente(id_cliente);
        if (lista != null && lista.size() > 0){
            return lista;
        } else {
            return new ArrayList<>();
        }

    }

    public SetEnderecos findAllById(Integer id_endereco) {
        Optional<SetEnderecos> optional = repository.findById(id_endereco);
        return optional.orElse(null);

    }

    public void deletar(SetEnderecos endereco) throws Exception {
        try {
            Optional<SetEnderecos> optional = repository.findById(endereco.getId_endereco());
            SetEnderecos entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }
}
