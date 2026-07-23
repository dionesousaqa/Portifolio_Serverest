package Contratos.Produtos;

import Produtos.core.BaseTest;
import Utils.SchemaPaths;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class GetQueryContratoProdutos extends BaseTest {
    @Test
    public void getContratoProdutQuery(){
        File jsonSchema = new File(SchemaPaths.GET_PRODUTOS_SCHEMA);
        produtosServerRest.getProdutosPath()
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema));
    }
}
