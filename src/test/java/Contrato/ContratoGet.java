package Contrato;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class ContratoGet {
    @Test
    public void ValidacaoDeContrato(){
        File jsonSchema = new File("src/test/resources/Schemas/ContratoGet.json");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://serverest.dev/usuarios")
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(jsonSchema));


                ;
    }
}
