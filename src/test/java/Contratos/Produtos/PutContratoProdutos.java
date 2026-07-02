package Contratos.Produtos;

import Produtos.core.BaseTest;
import Utils.SchemaPaths;
import core.ObjetosProdutos;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.deletProdutos;
import static Utils.TestesUtils.postProdutos;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class PutContratoProdutos extends BaseTest {
    @Test
    public void putContratoProdutos() {
        String id = postProdutos();
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 90");
        objetosProdutos.setPreco(2500);
        objetosProdutos.setDescricao("Tv, tela Oled");
        objetosProdutos.setQuantidade(90);

        File jsonSchema = new File(SchemaPaths.PUT_PRODUTOS_SCHEMA);
        RestAssured.given()
                .body(objetosProdutos)
                .pathParam("_id",id)
                .when()
                .put("{_id}")
                .then()
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema));
        deletProdutos(id);
    }
}
