package Servicos;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_OK;

public class Autenticacao {

    public static String tokenBearer(Response response) {

        AutenticacaoDto autenticacaoDto = new AutenticacaoDto();

        autenticacaoDto.setEmail(response.path("usuarios.email[0]"));
        autenticacaoDto.setPassword(response.path("usuarios.password[0]"));

     return  RestAssured.given()
                .contentType("application/json")
                .body(autenticacaoDto)
                .when()
                .post("https://serverest.dev/login")
                .then()
                .statusCode(SC_OK).extract().path("authorization")
                ;
    }

}
