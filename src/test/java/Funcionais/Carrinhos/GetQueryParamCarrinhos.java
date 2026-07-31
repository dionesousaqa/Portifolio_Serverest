package Funcionais.Carrinhos;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static Utils.TestesUtils.*;
import static org.apache.http.HttpStatus.*;

public class GetQueryParamCarrinhos extends BaseTest {
    @Test
    public void getQueryCarrinhoIdValido() {
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        carrinhoServerRest.getCarrinhosQuery(Map.of("_id", id))
                .statusCode(SC_OK)
        ;
        deletCarrinho(TOKEN);
        deletProdutos(idProduto);
        deletUsuario(idUsuario);

    }

    @Test
    public void getQueryCarrinhosPrecoValido() {
        carrinhoServerRest.getCarrinhosQuery(Map.of("precoTotal", 1500))
                .statusCode(SC_OK);
        deletUsuario(idUsuario);
    }

    @Test
    public void getQueryCarrinhosQuantidadeValida() {
        carrinhoServerRest.getCarrinhosQuery(Map.of("quantidadeTotal", 200))
                .statusCode(SC_OK);
        deletUsuario(idUsuario);
    }

    @Test
    public void getQueryCarrinhosIdInvalido() {
        carrinhoServerRest.getCarrinhosQuery(Map.of("_id", "0020122478458742"))
                .statusCode(SC_OK);
        deletUsuario(idUsuario);
    }

    @Test
    public void getQueryCarrinhosPrecoInvalido() {
        carrinhoServerRest.getCarrinhosQuery(Map.of("precoTotal", 0))
                .statusCode(SC_BAD_REQUEST).log().all()
                .body("precoTotal", Matchers.is("precoTotal deve ser um número positivo"));

        deletUsuario(idUsuario);
    }

    @Test
    public void getQueryCarrinhosQuantidadeInvalida() {
        carrinhoServerRest.getCarrinhosQuery(Map.of("quantidadeTotal", -1))
                .statusCode(SC_BAD_REQUEST)
                .body("quantidadeTotal", Matchers.is("quantidadeTotal deve ser um número positivo"));
        deletUsuario(idUsuario);
    }

    @Test
    public void getQueryCarrinhosParametrosVazio() {
        carrinhoServerRest.getCarrinhosQuery(Map.of("_id", "", "precoTotal", "",
                        "quantidadeTotal", "", "idUsuario", ""))
                .statusCode(SC_BAD_REQUEST).log().all();
        deletUsuario(idUsuario);

    }
    @Test
    public void getQueryCarrinhosSemParametros() {
        carrinhoServerRest.getCarrinhosQuery(Map.of())
                .statusCode(SC_OK).log().all();
        deletUsuario(idUsuario);

    }
}
