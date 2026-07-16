package Contratos.Carrinhos;

import Carrinhos.BaseTest;
import Utils.SchemaPaths;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.mozilla.javascript.Token;

import java.io.File;

import static Utils.TestesUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class GetQueryContratoCarrinhos extends BaseTest {
    @Test
    public void getQueryContratoCarrinhos() {
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        File jsonSchema = new File(SchemaPaths.GET_CARRINHO_QUEY_SCHEMA);
        RestAssured.given()
                .queryParam(_ID, id)
                .when()
                .get(CARRINHOS)
                .then()
                .statusCode(SC_OK)
                .log().all()
                .body(matchesJsonSchema(jsonSchema));
        ;
        deletCarrinho(TOKEN);
        deletUsuario(idUsuario);
        deletProdutos(idProduto);
    }
}
