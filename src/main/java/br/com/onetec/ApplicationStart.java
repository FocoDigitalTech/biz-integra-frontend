package br.com.onetec;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Inline;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@Theme(value = "front-end-nagazaki-biz-integra")
@Push(PushMode.AUTOMATIC)
//@PWA(name = "Nagasaki", shortName = "App", iconPath  = "https://cdn.iconscout.com/icon/premium/png-256-thumb/html-2752158-2284975.png") // Defina o caminho para o favicon
public class ApplicationStart implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }


}
