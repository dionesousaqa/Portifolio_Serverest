package Funcionais.Carrinhos;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.Test;

import static Utils.TestesUtils.*;
import static Utils.TestesUtils.deletUsuario;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class DelCancelarComprasCarrinhos extends BaseTest {
    @Test
    public void delCancelComprasTokenValido() {
        String id = postProdutos();
        String id2 = postCarrinhos(id);

        carrinhoServerRest.delCarrinhosCancellCompras();

        deletProdutos(id);
        deletUsuario(idUsuario);
    }

    @Test
    public void delCancelarComprasTokenAusente() {
        String id = postProdutos();
        String id2 = postCarrinhos(id);

        RequestSpecBuilder recBuildeer = new RequestSpecBuilder();
        recBuildeer.addHeader(AUTHORIZATION, TOKEN_AUSENTE);
        RestAssured.requestSpecification = null;

        carrinhoServerRest.delCarrinhosCancellCompras()
                .statusCode(SC_UNAUTHORIZED)
                .body(MESSAGE,
                        equalTo(TOKEN_AUSENTE_INVALIDO_EXPIRADO));

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(APP_CONTENT_TYPE)
                .addHeader(AUTHORIZATION, TOKEN)
                .build();

        carrinhoServerRest.delCarrinhosCancellCompras()
                .statusCode(SC_OK);

        deletProdutos(id);
        deletUsuario(idUsuario);
    }
}
