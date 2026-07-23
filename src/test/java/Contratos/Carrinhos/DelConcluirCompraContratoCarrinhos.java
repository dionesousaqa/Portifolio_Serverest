package Contratos.Carrinhos;

import Carrinhos.BaseTest;
import Utils.SchemaPaths;

import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class DelConcluirCompraContratoCarrinhos extends BaseTest {
    @Test
    public void delConcluiComprasContratoCarrinhos() {
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        File jsonSchema = new File(SchemaPaths.DEL_CONC_COMPRA_CARRINHOS);
        carrinhoServerRest.delCarrinhosConcluCompras()
                .body(matchesJsonSchema(jsonSchema));
        ;
        deletProdutos(idProduto);
        deletUsuario(idUsuario);
    }
}
