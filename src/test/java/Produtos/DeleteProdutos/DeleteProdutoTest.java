package Produtos.DeleteProdutos;

import Produtos.core.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static Utils.Utilitarios.*;

import static Utils.TestesUtils.postProdutos;
import static org.apache.http.HttpStatus.*;

public class DeleteProdutoTest extends BaseTest {
    @Test
    public void ValidarIdExistente(){

       String id = postProdutos();

                RestAssured.given()
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                .when()
                        .delete(id)
                .then()
                        .statusCode(SC_OK).log().all()
                ;
    }
    @Test
    public void ValidarIdInvalido(){

              RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("10102014821410100")
                .then()
                .statusCode(SC_BAD_REQUEST).log().all()
        ;
    }
    @Test
    public void ValidarIdInexistente(){

        RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("1010201482141014")
                .then()
                .statusCode(SC_OK).log().all()
        ;
    }
    @Test
    public void ValidarIdVazio(){

        RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete()
                .then()
                .statusCode(SC_METHOD_NOT_ALLOWED).log().all()
        ;
    }
}
