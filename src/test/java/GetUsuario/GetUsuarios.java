package GetUsuario;

import core.BaseTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.deletUsuario;
import static Utils.TestesUtils.postUsuario;
import static Utils.Utilitarios.*;

public class GetUsuarios extends BaseTest {


    @Test
    public void devoListarTodosUsuarios() {

        RestAssured.given()
                   .when()
                        .get()
                   .then()
                        .statusCode(200)
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
                        .body(QUANTIDADE, Matchers.is(0))


        ;
    }

    @Test
    public void deveListarPorIdExistente() {
        String email = gerarEmailUnico();
        String id =postUsuario(email);

        RestAssured.given()
                         .queryParam("_id", id)
                    .when()
                         .get()
                    .then()
                        .statusCode(200)
                       .body("usuarios._id[0]", Matchers.is(id))


        ;
        deletUsuario(id);
    }

    @Test
    public void deveValidarNomeDeUsuario() {
        String email = gerarEmailUnico();
        String id = postUsuario(email);
        RestAssured.given()
                       .queryParam(NOME, "Antonio Pereira")
                   .when()
                      .get()
                   .then()
                      .statusCode(200)
                      .body(USUARIOS_NOME, Matchers.everyItem(Matchers.equalTo("Antonio Pereira")))


        ;
        deletUsuario(id);

    }

    @Test
    public void deveValidarNomeInexistente() {
        RestAssured.given()
                .queryParam(NOME , " Geraldo Amisterdã")
                    .when()
                        .get()
                    .then()
                        .statusCode(200)
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
                       .statusCode(200)
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
                      .statusCode(200)
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
                      .statusCode(200)
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
                      .statusCode(200)
                      .body( USUARIOS_ADMINISTRADOR,Matchers.everyItem(Matchers.equalTo("false")))

                ;
    }
}