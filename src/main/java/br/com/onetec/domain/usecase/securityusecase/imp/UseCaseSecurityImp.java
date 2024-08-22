package br.com.onetec.domain.usecase.securityusecase.imp;

import br.com.onetec.infra.db.model.SetUsuarios;
import br.com.onetec.infra.db.repository.IUsuariosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UseCaseSecurityImp  {


    private IUsuariosRepository repository;

    @Autowired
    public void initServices(IUsuariosRepository repository1) {
        this.repository = repository1;
    }

    public List<User> getAllUsers() {
        List<SetUsuarios> usuarios = repository.listAll();
        List<User> lista = new ArrayList<>();
        usuarios.forEach(u -> {
            lista.add((User) User.builder()
                    .username(u.getNome_usuario())
                    .password(passwordEncoder().encode(u.getSenha_usuario()))
                    .roles(Roles.ADMIN)
                    .build());
        });

        return lista;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SetUsuarios getUserByUserName(String username) {
       return repository.findByusername(username);
    }
}
