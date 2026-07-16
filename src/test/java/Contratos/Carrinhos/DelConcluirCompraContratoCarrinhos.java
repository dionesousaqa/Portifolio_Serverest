package Contratos.Carrinhos;

import Carrinhos.BaseTest;
import Utils.SchemaPaths;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.TestesUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class DelConcluirCompraContratoCarrinhos extends BaseTest {
    @Test
    public void delConcluiComprasContratoCarrinhos(){
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        File jsonSchema= new File(SchemaPaths.DEL_CONC_COMPRA_CARRINHOS);

        RestAssured.given()
                .when().log().all()
                .delete(CARRINHO_CONCLUIR_COMPRA)
                .then().log().all()
                .body(matchesJsonSchema(jsonSchema));
                ;
        deletProdutos(idProduto);
        deletUsuario(idUsuario);
    }
}
