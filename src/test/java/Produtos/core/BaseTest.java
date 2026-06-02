package Produtos.core;

import Servicos.Autenticacao;
import Utils.Constantes;
import Utils.TestesUtils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

import static Utils.TestesUtils.getUsuariosLogin;

public class BaseTest implements Constantes {
    protected static String TOKEN;
  @BeforeAll
public static void setup(){
      RestAssured.baseURI = APP_BASE_URL;
      RestAssured.basePath =APP_BASE_PATH_PRODUTOS;

      RequestSpecBuilder recBuilder = new RequestSpecBuilder();
      recBuilder.setContentType(APP_CONTENT_TYPE);
      RestAssured.requestSpecification = recBuilder.build();

      RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
      TOKEN = Autenticacao.tokenBearer(getUsuariosLogin());



  }

}
