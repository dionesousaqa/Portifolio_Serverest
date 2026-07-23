package Contratos.Carrinhos;

import Carrinhos.BaseTest;
import Utils.SchemaPaths;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.*;

public class GetPathContratoCarrinhos extends BaseTest {
    @Test
    public void getContratoCarrinhoPath() {
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        File jsonSchema = new File(SchemaPaths.GET_CARINHOS_PATH_SCHEMA);
        carrinhoServerRest.getCarrinhoPathId(id)
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema));
        ;
        deletCarrinho(TOKEN);
        deletProdutos(idProduto);
        deletUsuario(idUsuario);

    }

}
