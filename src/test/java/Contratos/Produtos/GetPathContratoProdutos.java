package Contratos.Produtos;

import Produtos.core.BaseTest;
import Utils.SchemaPaths;

import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.postProdutos;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.*;


public class GetPathContratoProdutos extends BaseTest {
    @Test
    public void getContratoPathProdutos() {
        String id = postProdutos();
        File jsonSchema = new File(SchemaPaths.GET_PRODUTOS_PATH_SCHEMA);

        produtosServerRest.getProdutosPathId(id)
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema))
        ;
        produtosServerRest.delProdutos(id);
    }
}