import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.RecordingState;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.net.ResponseCache;

import static Utils.Utilitarios.*;

public class GetUsuarios extends BaseTest{


    @Test
    public void devoListarTodosUsuarios() {

        RestAssured.given()
                   .when()
                        .get()
                   .then()
                        .statusCode(200)
                        .log().all()
                        .body(QUANTIDADE, Matchers.greaterThan(0))

        ;
    }

    @Test
    public void validarExcecaoAoPassarIdInexistente() {
        RestAssured.given()
                        .queryParam("_id", "12345")
                   .when()
                        .get()
                   .then()
                        .statusCode(200)
                        .log().all()
                        .body(QUANTIDADE, Matchers.is(0))


        ;
    }

    @Test
    public void deveListarPorIdExistente() {
        RestAssured.given()
                         .queryParam("_id", "0uxuPY0cbmQhpEz1")
                    .when()
                         .get()
                    .then()
                        .statusCode(200)
                        .log().all()
                        .body("usuarios._id[0]", Matchers.is("0uxuPY0cbmQhpEz1"))


        ;
    }

    @Test
    public void deveValidarNomeDeUsuario() {
        RestAssured.given()
                       .queryParam(NOME, "Fulano da Silva")
                   .when()
                      .get()
                   .then()
                      .statusCode(200)
                      .log().all()
                      .body(USUARIOS_NOME, Matchers.everyItem(Matchers.equalTo("Fulano da Silva")))


        ;

    }

    @Test
    public void deveValidarNomeInexistente() {
        RestAssured.given()
                .queryParam(NOME , " Geraldo Amisterdã")
                    .when()
                        .get()
                    .then()
                        .statusCode(200).log().all()
                        .body(QUANTIDADE, Matchers.is(0))


                ;

    }

    @Test
    public void deveValidarEmailExistente(){
        RestAssured.given()
                       .queryParam(EMAIL, "beltrano@qa.com.br")
                    .when()
                       .get()
                    .then()
                       .statusCode(200).log().all()
                       .body( USUARIOS_EMAIL, Matchers.everyItem(Matchers.equalTo("beltrano@qa.com.br")))

                ;

    }

    @Test
    public void deveValidarEmailInexistente(){
        RestAssured.given()
                      .queryParam(EMAIL,"teste3mail@hotmail.com")
                   .when()
                       .get()
                   .then()
                      .statusCode(200).log().all()
                      .body(QUANTIDADE, Matchers.is(0))

                ;

    }
    @Test
    public void deveValidarAdministradorTrue(){
        RestAssured.given()
                       .queryParam(ADMINISTRADOR,"true")
                    .when()
                       .get()
                    .then()
                      .statusCode(200).log().all()
                      .body( USUARIOS_ADMINISTRADOR,Matchers.everyItem(Matchers.equalTo("true")))
                ;
    }
    @Test
    public void deveValidarAdministradorFalse(){
        RestAssured.given()
                       .queryParam(ADMINISTRADOR, "false")
                    .when()
                       .get()
                    .then()
                      .statusCode(200).log().all()
                      .body( USUARIOS_ADMINISTRADOR,Matchers.everyItem(Matchers.equalTo("false")))

                ;
    }
}