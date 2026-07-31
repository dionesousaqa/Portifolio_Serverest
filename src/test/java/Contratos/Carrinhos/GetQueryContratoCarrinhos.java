package Contratos.Carrinhos;
import Funcionais.Carrinhos.BaseTest;
import Utils.SchemaPaths;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Map;

import static Utils.TestesUtils.*;
import static Utils.Utilitarios.ID;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class GetQueryContratoCarrinhos extends BaseTest {
    @Test
    public void getQueryContratoCarrinhos() {
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        File jsonSchema = new File(SchemaPaths.GET_CARRINHO_QUEY_SCHEMA);

        carrinhoServerRest.getCarrinhosQuery(Map.of(ID, id))
                .statusCode(SC_OK)
                .log().all()
                .body(matchesJsonSchema(jsonSchema));
        ;
        deletCarrinho(TOKEN);
        deletProdutos(idProduto);
        deletUsuario(idUsuario);
    }
}
