package Carrinhos;

import Componentes.Carrinhos.CarrinhosServerRest;
import Utils.Constantes;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;


import static Servicos.Autenticacao.tokenBearer;
import static Utils.TestesUtils.*;
import static Utils.Utilitarios.AUTHORIZATION;

public class BaseTest implements Constantes {
    protected static String TOKEN;
    protected static String idUsuario;
    public static final CarrinhosServerRest carrinhoServerRest = new CarrinhosServerRest();
    @BeforeAll
    public static void setup() {

        RestAssured.baseURI = APP_BASE_URL;

        RequestSpecBuilder recBuilder = new RequestSpecBuilder();
        recBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification = recBuilder.build();

        idUsuario = postUsuario("toinfulano26@qa.com.br");
        Response response = getUsuariosLoginCarrinho(idUsuario);
        TOKEN = tokenBearer(response);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RequestSpecBuilder recBuildeer = new RequestSpecBuilder();
        recBuildeer.addHeader(AUTHORIZATION, TOKEN);
        recBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification = recBuildeer.build();
    }
}


