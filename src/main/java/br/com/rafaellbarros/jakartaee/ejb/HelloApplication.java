package br.com.rafaellbarros.jakartaee.ejb;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
@OpenAPIDefinition(info = @Info(
        title = "JakartaEE EJb Application",
        version = "1.0.0",
        contact = @Contact(
                name = "Rafael Barros",
                email = "rafaelbarros.df@gmail.com",
                url = "http://www.xablau.com")
),
        servers = {
                @Server(url = "/example",description = "localhost")
        }
)
public class HelloApplication extends Application {

}