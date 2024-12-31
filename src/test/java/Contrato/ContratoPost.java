package Contrato;

import core.BaseTest;
import core.ObjetosUsuarios;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static Utils.MetodosUltils.gerarEmailUnico;
import java.io.File;

import static Utils.TestesUtils.deletUsuario;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class ContratoPost extends BaseTest  {
    @Test
    public void ValidacaoContratoPost (){
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
         objetosUsuarios.setPassword("1S34$");
         objetosUsuarios.setNome("José Luiz");
         objetosUsuarios.setAdministrador("true");
         objetosUsuarios.setEmail(gerarEmailUnico());

        File jsonSchema = new File("src/test/resources/Schemas/ContratoPost.json");

       String id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objetosUsuarios)
                .when()
                .post("https://serverest.dev/usuarios")
                .then()
                .statusCode(201)
                .body(matchesJsonSchema(jsonSchema))
                .extract().path("_id")


                ;
        deletUsuario(id);
    }
}
