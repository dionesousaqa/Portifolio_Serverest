package PostUsuarios;

import core.BaseTest;
import core.ObjetosUsuarios;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.deletUsuario;
import static Utils.TestesUtils.postUsuario;


public class PostUsuarios extends BaseTest {
    @Test
    public void deveInserirUsuarioComSucesso(){
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail(gerarEmailUnico());
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("TesteSeguranca");

       Response response = RestAssured.given()
                       .body(objetosUsuarios)
                   .when()
                       .post()
                   .then()
                      .statusCode(201).log().all()
                      .body("message", Matchers.is("Cadastro realizado com sucesso"))
                      .extract().response()
        ;
        deletUsuario(response.path("_id"));
    }
    @Test
    public void validandoCampoAdministradorVazio(){
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("");
        objetosUsuarios.setEmail("usuariopadrao3@hotmail.com");
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("TesteSeguranca");

        RestAssured.given()
                .body(objetosUsuarios)
                .when()
                .post()
                .then()
                .statusCode(400).log().all()
                .body("administrador", Matchers.is("administrador deve ser 'true' ou 'false'"))
        ;
    }
    @Test
    public void validandoCampoEmailVazio(){
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail("");
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("TesteSeguranca");

        RestAssured.given()
                .body(objetosUsuarios)
                .when()
                .post()
                .then()
                .statusCode(400).log().all()
                .body("email", Matchers.is("email não pode ficar em branco"))
        ;
    }
    @Test
    public void validandoCampoNomeVazio(){
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail("usuariopadrao5@hotmail.com");
        objetosUsuarios.setNome("");
        objetosUsuarios.setPassword("TesteSeguranca");

        RestAssured.given()
                .body(objetosUsuarios)
                .when()
                .post()
                .then()
                .statusCode(400).log().all()
                .body( "nome", Matchers.is("nome não pode ficar em branco"))
        ;
    }

    @Test
    public void validandoCampoPasswordVazio(){
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail("usuariopadrao7@hotmail.com");
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("");

        RestAssured.given()
                .body(objetosUsuarios)
                .when()
                .post()
                .then()
                .statusCode(400).log().all()
                .body( "password", Matchers.is("password não pode ficar em branco"))
        ;
    }
    @Test
    public void validandoCampoEmailInvalido(){
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail("123");
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("TesteSeguranca");

        RestAssured.given()
                .body(objetosUsuarios)
                .when()
                .post()
                .then()
                .statusCode(400).log().all()
                .body( "email", Matchers.is("email deve ser um email válido"))
        ;
    }

    @Test
    public void validandoCampoEmailExistente(){
     String id = postUsuario("AntonioPereira7650@hotmail.com");

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail("AntonioPereira7650@hotmail.com");
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("TesteSeguranca");

        RestAssured.given()
                .body(objetosUsuarios)
                .when()
                .post()
                .then()
                .statusCode(400).log().all()
                .body( "message", Matchers.is("Este email já está sendo usado"))
        ;
       deletUsuario(id);
    }
}
