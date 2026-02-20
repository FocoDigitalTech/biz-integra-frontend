package br.com.onetec.application.service.enderecoservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Endereco;
import br.com.onetec.application.service.clientesservice.ClientesService;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetEnderecos;
import br.com.onetec.infra.db.repository.IEnderecosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class EnderecoService {

    @Autowired
    IEnderecosRepository repository;

    @Autowired
    ClientesService clientesService;

    public void save(List<Endereco> enderecos, Integer idCliente, Integer idUsuario) {
        enderecos.forEach(e -> {
            SetEnderecos et = new SetEnderecos();
            et.setAtivo("S");
            et.setId_regiao(e.getComboEnderecosRegiao().getId_regiao());
            et.setId_tipoimovel(e.getComboEnderecosTipoImovel().getId_tipoimovel());
            et.setArea_imovel(e.getFieldEnderecosArea());
            et.setBairro_imovel(e.getFieldEnderecosBairro());
            et.setCep_imovel(e.getFieldEnderecosCEP());
            et.setEnderecoImovel(e.getFieldEnderecosEndereço());
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
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return new ArrayList<>();
        }

    }

    public SetEnderecos findById(Integer id_endereco) {
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
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SetEnderecos> updateListaNova(List<Endereco> enderecosLista, Integer id_cliente, Integer id_usuario) {
        List<SetEnderecos> listaSalva = new ArrayList<>();
        enderecosLista.forEach(e -> {
            SetEnderecos et = new SetEnderecos();
            et.setAtivo("S");
            et.setId_regiao(e.getComboEnderecosRegiao().getId_regiao());
            et.setId_tipoimovel(e.getComboEnderecosTipoImovel().getId_tipoimovel());
            et.setArea_imovel(e.getFieldEnderecosArea());
            et.setBairro_imovel(e.getFieldEnderecosBairro());
            et.setCep_imovel(e.getFieldEnderecosCEP());
            et.setEnderecoImovel(e.getFieldEnderecosEndereço());
            et.setCidade_imovel(e.getFieldEnderecosCidade());
            et.setComplemento_imovel(e.getFieldEnderecosComplemento());
            et.setData_inclusao(LocalDateTime.now());
            et.setId_cliente(id_cliente);
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
        listaSalva = findAllClienteId(id_cliente);
        return listaSalva;
    }

    public void update(SetEnderecos item) throws Exception {
        try {
            Optional<SetEnderecos> optional = repository.findById(item.getId_endereco());
            SetEnderecos entity = optional.get();
            entity = item;
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Atualizado !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SetEnderecos> findAllByEnderecoNome(String value) {
        List<SetEnderecos> lista = new ArrayList<>();
        Set<Integer> idsClientesAdicionados = new HashSet<>(); // Set para armazenar IDs únicos de clientes

        repository.findByEnderecoImovelContainingIgnoreCase(value).forEach(p -> {
            if (p.getAtivo().equals("S") && validaClienteAtivo(p)) {
                Integer idCliente = p.getId_cliente(); // Assumindo que esse seja o campo de ID do cliente
                if (!idsClientesAdicionados.contains(idCliente)) {
                    lista.add(p);
                    idsClientesAdicionados.add(idCliente); // Marca o ID como adicionado
                }
            }
        });

        return lista;
    }


    private boolean validaClienteAtivo(SetEnderecos p) {
        SetCliente cliente = clientesService.findById(p.getId_cliente());
        if (Objects.nonNull(cliente)) {
            if (cliente.getAtivo().equals("S")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
