package Funcionais.Login;

import Componentes.Login.LoginServerRest;
import Utils.Constantes;
import Utils.TestesUtils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest implements Constantes {
    public static final LoginServerRest loginServerRest = new LoginServerRest();
    public static final TestesUtils testesUtils = new TestesUtils();

    @BeforeAll
    public static void setup() {

        RestAssured.baseURI = APP_BASE_URL;

        RequestSpecBuilder recBuilder = new RequestSpecBuilder();
        recBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification = recBuilder.build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
