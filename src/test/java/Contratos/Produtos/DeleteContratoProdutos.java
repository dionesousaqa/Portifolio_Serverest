package Contratos.Produtos;

import Produtos.core.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.SchemaPaths.DEL_PRODUTOS_SCHEMA;
import static Utils.TestesUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class DeleteContratoProdutos extends BaseTest {
    @Test
    public void deleteContratoProdutos(){
        String id = postProdutos();
        File jsonSchema = new File(DEL_PRODUTOS_SCHEMA);
        produtosServerRest.delProdutos(id)
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema))
                ;
        produtosServerRest.delProdutos(id);
    }
}
