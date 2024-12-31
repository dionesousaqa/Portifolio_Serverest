package Contrato;

import core.BaseTest;
import core.ObjetosUsuarios;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;

import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.deletUsuario;
import static Utils.TestesUtils.postUsuario;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class ContratoPut extends BaseTest {
    @Test
    public void ValidarContatoPut(){
         String id = postUsuario(gerarEmailUnico());

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
         objetosUsuarios.setPassword("12345"+ "Alterado");
         objetosUsuarios.setEmail("Alterado"+gerarEmailUnico());
         objetosUsuarios.setNome("Luiz José" + "Alterado");
         objetosUsuarios.setAdministrador("false");

        File jsonSchema = new File("src/test/resources/Schemas/ContratoPut.json");


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objetosUsuarios)
                .pathParam("_id",id)
                .when()
                .put("https://serverest.dev/usuarios/{_id}")
               //OU >> .put("https://serverest.dev/usuarios/"+id)
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(jsonSchema))

                ;
        deletUsuario(id);
    }
}
