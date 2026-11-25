package mx.acreditadora.rest;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.gson.JsonGsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Punto de entrada de la API en Tomcat.
 * Reemplaza al Main (Grizzly) del proyecto de VS Code:
 * registra los recursos REST y el soporte JSON con Gson.
 *
 * URL base: http://localhost:8080/Acreditadora1/api
 */
@ApplicationPath("/api")
public class RestApplication extends ResourceConfig {

    public RestApplication() {
        packages("mx.acreditadora.rest");
        register(JsonGsonFeature.class);
    }
}
