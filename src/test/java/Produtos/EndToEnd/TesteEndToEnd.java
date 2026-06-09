package Produtos.EndToEnd;

import Produtos.core.BaseTest;
import core.ObjetosProdutos;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static Utils.TestesUtils.*;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class TesteEndToEnd extends BaseTest {
    @Test
    public void postProdutosE2E() {

        String id = postProdutos();

        RestAssured.given()
                .queryParam(ID, id)
                .when()
                .get()
                .then()
                .statusCode(SC_OK)
                .body(QUANTIDADE, equalTo(1))
                .body(PRODUTO_ID_PRIMEIRO, equalTo(id))
        ;
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_90);
        objetosProdutos.setPreco(15);
        objetosProdutos.setDescricao(TV_TELA_OLED);
        objetosProdutos.setQuantidade(3);

        RestAssured.given()
                .body(objetosProdutos)
                .when()
                .put(id)
                .then()
                .statusCode(SC_OK).body(MESSAGE, equalTo(REGISTRO_ALTERADO))
        ;
        RestAssured.given()
                .when()
                .get(id)
                .then()
                .statusCode(SC_OK)
                .body(NOME,equalTo(TV_SAMSUNG_90))
                .body(PRECO,equalTo(15))
                .body(DESCRICAO,equalTo(TV_TELA_OLED))
                .body(QUANTIDADE,equalTo(3))
                .body(ID, equalTo(id))

                ;

        deletProdutos(id);
    }
}
