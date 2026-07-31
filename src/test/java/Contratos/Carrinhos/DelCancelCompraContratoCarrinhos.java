package Contratos.Carrinhos;

import Funcionais.Carrinhos.BaseTest;
import Utils.SchemaPaths;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.*;
import static Utils.TestesUtils.deletUsuario;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class DelCancelCompraContratoCarrinhos extends BaseTest {
    @Test
    public void delCancelComprasContratoCarrinhos() {
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        File jsonSchema = new File(SchemaPaths.DEL_CANCEL_COMPRA_CARRIHOS);

        carrinhoServerRest.delCarrinhosCancellCompras()
                .body(matchesJsonSchema(jsonSchema));
        ;
        deletProdutos(idProduto);
        deletUsuario(idUsuario);
    }
}

