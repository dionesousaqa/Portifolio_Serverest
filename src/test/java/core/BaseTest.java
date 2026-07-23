package core;


import Componentes.Usuarios.UsuariosServerRest;
import Utils.Constantes;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;


public class BaseTest implements Constantes {
    public static final UsuariosServerRest usuariosServerRest = new UsuariosServerRest();

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = APP_BASE_URL;
       // RestAssured.basePath = APP_BASE_PATH;

        RequestSpecBuilder recBuilder = new RequestSpecBuilder();
        recBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification = recBuilder.build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }
}
