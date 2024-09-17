package br.com.onetec.application.security;

import br.com.onetec.infra.db.model.SetUsuarios;
import br.com.onetec.infra.db.repository.ISetGrupoUsuarioRepository;
import br.com.onetec.infra.db.repository.IUsuariosRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final IUsuariosRepository userRepository;

    public MyUserDetailsService(IUsuariosRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        SetUsuarios usuario = userRepository.findByusername(username);
        if(usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal((User) User.builder()
                .username(usuario.getNome_usuario())
                .password(passwordEncoder().encode(usuario.getSenha_usuario()))
                .build());
    }

        public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
