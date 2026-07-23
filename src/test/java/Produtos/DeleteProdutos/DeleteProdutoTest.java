package Produtos.DeleteProdutos;

import Produtos.core.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static Utils.TestesUtils.postProdutos;
import static org.apache.http.HttpStatus.*;

public class DeleteProdutoTest extends BaseTest {
    @Test
    public void ValidarIdExistente() {

        String id = postProdutos();
        produtosServerRest.delProdutos(id)
                .statusCode(SC_OK).log().all()
        ;
    }
    @ParameterizedTest
    @MethodSource("idsInvalido")
    public void ValidarIdInvalido(String id) {

        produtosServerRest.delProdutos("idsInvalido")
                .statusCode(SC_BAD_REQUEST).log().all()
        ;
    }

    @Test
    public void ValidarIdInexistente() {

        produtosServerRest.delProdutos("1010201482141014")
                .statusCode(SC_OK).log().all()
        ;
    }

    @Test
    public void ValidarIdVazio() {

        produtosServerRest.delProdutos("")
                .statusCode(SC_METHOD_NOT_ALLOWED).log().all()
        ;
    }

    static Stream<Arguments> idsInvalido() {
        return Stream.of(Arguments.of("101020148214101"),
                Arguments.of("10102014821410100"));
    }
}
