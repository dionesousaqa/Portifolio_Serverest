package Produtos.GetProdutos;

import Produtos.core.BaseTest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static Utils.TestesUtils.*;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetProdutos extends BaseTest {
    @Test
    public void getProdutos() {
        String id = postProdutos();

        Response response = produtosServerRest.getProdutosPathId(id)
                .statusCode(SC_OK).extract().response();

        validarProdutosPath(response, id);
        produtosServerRest.delProdutos(id);
    }

    @Test
    public void getIdInexistente() {

        produtosServerRest.getProdutosPathId(ID_INEXISTENTE)
                .statusCode(SC_BAD_REQUEST).body(MESSAGE, equalTo(PRODUTO_NAO_ENCONTRADO)).log().all()
        ;
    }

    @ParameterizedTest
    @MethodSource("idInvalidos")
    public void getIdsInvalidos(String idInvalidos) {
        produtosServerRest.getProdutosPathId(idInvalidos)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(BODY_ID, equalTo(ID_DEVE_TER_EXATAMENTE_16_CARACTERES))
                .log().all();
    }

    static Stream<Arguments> idInvalidos() {
        return Stream.of(Arguments.of("9pUdIIvNAchoJSS"),
                Arguments.of("9pUdIIvNAchoJSSss"));
    }

    @Test
    public void campoNomeInexistente() {

        produtosServerRest.getProdutosQuery(Map.of(NOME, TV_SAMSUNG_70))
                .statusCode(SC_OK).body(QUANTIDADE, equalTo(NUMERAL_ZERO)).log().all()
        ;
    }

    @Test
    public void campoIdInexistenteQuery() {
        produtosServerRest.getProdutosQuery(Map.of(ID, "1pUdIIvNAchSS"))
                .statusCode(SC_OK).log().all()
                .body(QUANTIDADE, equalTo(NUMERAL_ZERO))
                .body(PRODUTOS, empty())
        ;
    }

    @Test
    public void campoIdExistenteQuery() {
        String id = postProdutos();
        Response response =
                produtosServerRest.getProdutosQuery(Map.of(ID, id))
                        .statusCode(SC_OK).log().all()
                        .extract().response();

        validarProduto(response, id);

        produtosServerRest.delProdutos(id);
    }

    @Test
    public void campoNomeValidoQuery() {
        String id = postProdutosAll(TV_SAMSUNG_60, 150000, TV_TELA_AMOLED, 500);

        produtosServerRest.getProdutosQuery(Map.of(NOME, TV_SAMSUNG_60))
                .statusCode(SC_OK).log().all()
        ;
        produtosServerRest.delProdutos(id);
    }

    @Test
    public void campoNomeVazioQuery() {
        produtosServerRest.getProdutosQuery(Map.of(NOME, ""))
                .statusCode(SC_OK).log().all()
        ;
    }

    @Test
    public void campoNomeInexistenteQuery() {
        produtosServerRest.getProdutosQuery(Map.of(NOME, TV_SAMSUNG_600))
                .statusCode(SC_OK).log().all()
                .body(QUANTIDADE, equalTo(NUMERAL_ZERO))
                .body(PRODUTOS, empty())
        ;
    }

    @Test
    public void campoPrecoValidoQuery() {
        String id = postProdutosAll(TV_SAMSUNG_60, 150000, TV_TELA_AMOLED, 500);

        Response response = produtosServerRest.getProdutosQuery(Map.of(PRECO, 150000))
                .statusCode(SC_OK).log().all()
                .extract().response();

        validarProduto(response, id);
        produtosServerRest.delProdutos(id);
    }

    @Test
    public void campoPrecoVazioQuery() {

        produtosServerRest.getProdutosQuery(Map.of(PRECO, ""))
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(PRECO, equalTo(PRECO_DEVE_SER_POSITIVO))
        ;

    }

    @ParameterizedTest
    @MethodSource("precoInvalidos")
    public void campoPrecoInvalidoQuery(Object precoInvalidos) {

        produtosServerRest.getProdutosQuery(Map.of(PRECO, precoInvalidos))
                .statusCode(SC_BAD_REQUEST).log().all()
        ;
    }

    static Stream<Arguments> precoInvalidos() {
        return Stream.of(Arguments.of(1.500),
                Arguments.of("abc"));
    }

    @Test
    public void campoDescricaoExistenteQuery() {
        String id = postProdutosAll(TV_SAMSUNG_60, 150000, TV_TELA_AMOLED, 500);

        Response response = produtosServerRest.getProdutosQuery(Map.of(DESCRICAO, TV_TELA_AMOLED))
                .statusCode(SC_OK).log().all()
                .extract().response();
        ;
        validarProduto(response, id);
        produtosServerRest.delProdutos(id);
    }

    @Test
    public void campoDescricaoInexistenteQuey() {

        produtosServerRest.getProdutosQuery(Map.of(DESCRICAO, TV_SAMSUNGPRIME_60))
                .statusCode(SC_OK).log().all()
                .body(QUANTIDADE, equalTo(NUMERAL_ZERO))
                .body(PRODUTOS, empty())
        ;
    }

    @Test
    public void campoDescricaoVazioQuery() {

        produtosServerRest.getProdutosQuery(Map.of(DESCRICAO, ""))
                .statusCode(SC_OK).log().all()
        ;
    }

    @Test
    public void campoQuantidadeValidoQuery() {
        String id = postProdutosAll(TV_SAMSUNG_60, 150000, TV_TELA_AMOLED, 500);

        Response response = produtosServerRest.getProdutosQuery(Map.of(QUANTIDADE, 500))
                .statusCode(SC_OK).log().all()
                .extract().response();

        validarProduto(response, id);
        produtosServerRest.delProdutos(id);
    }

    @Test
    public void campoQuatidadeInvalidaQuery() {
        produtosServerRest.getProdutosQuery(Map.of(QUANTIDADE, -200))
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(QUANTIDADE, equalTo(QUANTIDADE_MAIOR_IGUAL_A_ZERO))
        ;
    }

    @Test
    public void campoQuantidadeVazioQuery() {
        produtosServerRest.getProdutosQuery(Map.of(QUANTIDADE, ""))
                .statusCode(SC_OK).log().all()
        ;
    }
}