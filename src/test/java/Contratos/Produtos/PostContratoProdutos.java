package Contratos.Produtos;

import Produtos.core.BaseTest;
import Utils.SchemaPaths;
import core.ObjetosProdutos;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.*;

public class PostContratoProdutos extends BaseTest {
    @Test
    public void postContratoProdutos(){
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Logitech TVSmart");
        objetosProdutos.setPreco(1500);
        objetosProdutos.setDescricao("TV 50");
        objetosProdutos.setQuantidade(20);

        File jsonSchema = new File(SchemaPaths.POST_PRODUTOS_SCHEMA);
        String id = RestAssured.given()
                .body(objetosProdutos)
                .when()
                .post()
                .then()
                .statusCode(SC_CREATED)
                .body(matchesJsonSchema(jsonSchema))
                .extract().path("_id")
        ;
        deletProdutos(id);
    }
}
