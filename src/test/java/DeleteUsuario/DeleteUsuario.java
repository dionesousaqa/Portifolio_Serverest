package DeleteUsuario;

import core.BaseTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

import static Utils.TestesUtils.postUsuario;

public class DeleteUsuario extends BaseTest {
    @Test
    public void deletandoUsuario () {
        String id = postUsuario("AntonioPereira7650@hotmail.com");

        RestAssured.given()
                   .when()
                       .delete(id)
                   .then()
                      .statusCode(200).log().all()
                      .body("message", Matchers.is("Registro excluído com sucesso"))
                ;
    }
    @Test
    public void deletandoUsuarioComIdInexistente () {

        RestAssured.given()
                .when()
                    .delete("123")
                .then()
                   .statusCode(200).log().all()
                   .body("message", Matchers.is("Nenhum registro excluído"))
        ;
    }
}
