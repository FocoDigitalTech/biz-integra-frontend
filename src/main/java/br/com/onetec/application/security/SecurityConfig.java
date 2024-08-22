package br.com.onetec.application.security;


import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.securityservice.SecurityServiceImp;
import br.com.onetec.application.views.main.login.LoginView;
import br.com.onetec.infra.db.model.SetUsuarios;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
class SecurityConfig extends VaadinWebSecurity {

    private SecurityServiceImp service;


    @Autowired
    public void initServices(SecurityServiceImp service1) {
        this.service = service1;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService users() {
        return service.configSecuriry();
    }
}

