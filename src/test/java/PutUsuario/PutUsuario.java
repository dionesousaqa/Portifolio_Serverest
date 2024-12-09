package PutUsuario;

import core.BaseTest;
import core.ObjetosUsuarios;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.deletUsuario;
import static Utils.TestesUtils.postUsuario;

public class PutUsuario extends BaseTest {
    @Test
    public void devoAtualizarNomedeUsuario(){
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setNome("Antonio Zezim");
        objetosUsuarios.setPassword("TesteSeguranca");

            RestAssured.given()
                       .body(objetosUsuarios)
                   .when()
                      .put(id)
                   .then()
                     .statusCode(200).log().all()
                     .extract()
                     .response()


                ;
           deletUsuario(id);
    }

    @Test
    public void deveAlterarAdministrador(){
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
          objetosUsuarios.setAdministrador("false");
          objetosUsuarios.setEmail(email);
          objetosUsuarios.setNome("Antonio Zezim");
          objetosUsuarios.setPassword("TesteSeguranca");

        RestAssured.given()
                       .body(objetosUsuarios)
                   .when()
                       .put(id)
                   .then()
                      .statusCode(200).log().all()
                      .body("message", Matchers.is(("Registro alterado com sucesso")))

                ;
              deletUsuario(id);


    }
    @Test
    public void devoAlterarPassword(){
        String email = gerarEmailUnico();
        String id =postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
         objetosUsuarios.setAdministrador("false");
         objetosUsuarios.setNome("Antonio Zezim");
         objetosUsuarios.setEmail(email);
         objetosUsuarios.setPassword("TesteSeguança2024");

              RestAssured.given()
                           .body(objetosUsuarios)
                        .when()
                          .put(id)
                      .then()
                         .statusCode(200).log().all()
                         .body("message", Matchers.is("Registro alterado com sucesso"))

                      ;
              deletUsuario(id);
    }
    @Test
    public void devoAlterarEmail(){
        String email = gerarEmailUnico();
        String id =postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
         objetosUsuarios.setPassword("TesteSeguança2024");
         objetosUsuarios.setEmail("AntonioToinhoBede@gmail.com");
         objetosUsuarios.setAdministrador("true");
         objetosUsuarios.setNome("Antonio Zezim");

        RestAssured.given()
                       .body(objetosUsuarios)
                   .when()
                       .put(id)
                   .then()
                      .statusCode(200).log().all()
                      .body("message", Matchers.is("Registro alterado com sucesso"))

                ;
        deletUsuario(id);
    }

}
