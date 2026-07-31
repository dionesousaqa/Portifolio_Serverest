package Funcionais.Carrinhos;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.Test;

import static Utils.TestesUtils.*;
import static Utils.TestesUtils.deletUsuario;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class DelConcluirComprasCarrinhos extends BaseTest {
    @Test
    public void delTokenValidoCarrinhos() {
        String id = postProdutos();
        String id2 = postCarrinhos(id);

        carrinhoServerRest.delCarrinhosConcluCompras()
                .statusCode(SC_OK);

        deletProdutos(id);
        deletUsuario(idUsuario);
    }

    @Test
    public void delTokenAusenteCarrinhos() {
        String id = postProdutos();
        String id2 = postCarrinhos(id);

        RequestSpecBuilder recBuildeer = new RequestSpecBuilder();
        recBuildeer.addHeader(AUTHORIZATION, TOKEN_AUSENTE);
        RestAssured.requestSpecification = null;

        carrinhoServerRest
                .delCarrinhosConcluCompras()
                .statusCode(SC_UNAUTHORIZED)
                .body(MESSAGE,
                        equalTo(TOKEN_AUSENTE_INVALIDO_EXPIRADO));

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(APP_CONTENT_TYPE)
                .addHeader(AUTHORIZATION, TOKEN)
                .build();

        carrinhoServerRest.delCarrinhosConcluCompras()
                .statusCode(SC_OK);

        deletProdutos(id);
        deletUsuario(idUsuario);

    }
}
