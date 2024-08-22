package br.com.onetec.application.configuration;

import br.com.onetec.infra.db.model.SetUsuarios;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsuarioAutenticadoConfig {

    private static SetUsuarios user;

    public static SetUsuarios getUser() {
        return user;
    }

    public static void setUser(SetUsuarios user) {
        log.info("Usuario: " +user);
        UsuarioAutenticadoConfig.user = user;
    }
}
