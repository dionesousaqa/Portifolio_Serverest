package Contratos.Carrinhos;

import Carrinhos.BaseTest;
import Utils.SchemaPaths;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.*;
import static Utils.TestesUtils.deletUsuario;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class DelCancelCompraContratoCarrinhos extends BaseTest {
    @Test
    public void delCancelComprasContratoCarrinhos(){
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        File jsonSchema= new File(SchemaPaths. DEL_CANCEL_COMPRA_CARRIHOS);

        RestAssured.given()
                .when().log().all()
                .delete(CARRINHO_CANCELAR_COMPRA)
                .then().log().all()
                .body(matchesJsonSchema(jsonSchema));
        ;
        deletProdutos(idProduto);
        deletUsuario(idUsuario);
    }
}

