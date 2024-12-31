package Contrato;

import core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.File;

import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.postUsuario;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class ContratoDelete extends BaseTest {
    @Test
    public void ValidarContratoDelete(){
        String id = postUsuario(gerarEmailUnico());

        File jsonSchema = new File("src/test/resources/Schemas/ContratoDelete.json");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("_id", id)
                .when()
                .delete("https://serverest.dev/usuarios/{_id}")
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(jsonSchema))

                ;
    }
}
