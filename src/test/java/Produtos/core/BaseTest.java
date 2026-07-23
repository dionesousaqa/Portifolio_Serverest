package Produtos.core;

import Componentes.Produtos.ProdutosServerRest;
import Servicos.Autenticacao;
import Utils.Constantes;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

import static Utils.TestesUtils.getUsuariosLogin;
import static Utils.Utilitarios.AUTHORIZATION;

public class BaseTest implements Constantes {
    protected static String TOKEN;
    public static final ProdutosServerRest produtosServerRest = new ProdutosServerRest();

  @BeforeAll
public static void setup(){
      RestAssured.baseURI = APP_BASE_URL;
     // RestAssured.basePath =APP_BASE_PATH_PRODUTOS;
      RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
      TOKEN = Autenticacao.tokenBearer(getUsuariosLogin());

      RequestSpecBuilder recBuilder = new RequestSpecBuilder();
      recBuilder.addHeader(AUTHORIZATION, TOKEN);
      recBuilder.setContentType(APP_CONTENT_TYPE);

      RestAssured.requestSpecification = recBuilder.build();





  }

}
