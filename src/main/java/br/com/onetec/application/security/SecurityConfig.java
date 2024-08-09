package br.com.onetec.application.security;


import br.com.onetec.application.service.securityservice.ISecurityService;
import br.com.onetec.application.views.principal.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
class SecurityConfig extends VaadinWebSecurity {

    @Autowired
    ISecurityService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService users() {
//        var alice = User.builder()
//                .username("alice")
//                // password = password with this hash, don't tell anybody :-)
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles(Roles.USER)
//                .build();
//        var bob = User.builder()
//                .username("bob")
//                // password = password with this hash, don't tell anybody :-)
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles(Roles.USER)
//                .build();
//        var admin = User.builder()
//                .username("admin")
//                // password = password with this hash, don't tell anybody :-)
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles(Roles.ADMIN, Roles.USER)
//                .build();
       // return new InMemoryUserDetailsManager(alice, bob, admin);
        return service.configSecuriry();
    }
}

