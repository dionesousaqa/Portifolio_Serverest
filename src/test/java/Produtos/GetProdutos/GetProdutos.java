package Produtos.GetProdutos;

import Produtos.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

       Response response =  RestAssured.given()
                .when()
                .get(id)
                .then()
                .statusCode(SC_OK).log().all().extract().response();

        validarProdutosPath(response,id);
        deletProdutos(id);
    }

    @Test
    public void getIdInexistente() {
        RestAssured.given()
                .when()
                .get("9pUdIIvNAchoJSSS")
                .then()
                .statusCode(SC_BAD_REQUEST).body(MESSAGE, equalTo(PRODUTO_NAO_ENCONTRADO)).log().all()
        ;
    }

    @ParameterizedTest
    @MethodSource("idInvalidos")
    public void getIdsInvalidos(String idInvalidos) {
        RestAssured.given()
                .when()
                .get(idInvalidos)
                .then()
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

        RestAssured.given()
                .queryParam(NOME, TV_SAMSUNG_70)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).body(QUANTIDADE, equalTo(NUMERAL_ZERO)).log().all()
        ;
    }

    @Test
    public void campoIdInexistenteQuery() {
        RestAssured.given()
                .queryParam(ID, "1pUdIIvNAchSS")
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
                .body(QUANTIDADE, equalTo(NUMERAL_ZERO))
                .body(PRODUTOS, empty())
        ;
    }

    @Test
    public void campoIdExistenteQuery() {
        String id = postProdutos();

        Response response = RestAssured.given()
                .queryParam(ID, id)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
                .extract().response();

        validarProduto(response, id);

        deletProdutos(id);
    }

    @Test
    public void campoNomeValidoQuery() {
        String id = postProdutosAll(TV_SAMSUNG_60, 150000, TV_TELA_AMOLED, 500);
        RestAssured.given()
                .queryParam(NOME, TV_SAMSUNG_60)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
        ;
        deletProdutos(id);
    }

    @Test
    public void campoNomeVazioQuery() {

        RestAssured.given()
                .queryParam(NOME, "")
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
        ;
    }

    @Test
    public void campoNomeInexistenteQuery() {
        RestAssured.given()
                .queryParam(NOME, TV_SAMSUNG_600)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
                .body(QUANTIDADE, equalTo(NUMERAL_ZERO))
                .body(PRODUTOS, empty())
        ;
    }

    @Test
    public void campoPrecoValidoQuery() {
        String id = postProdutosAll(TV_SAMSUNG_60, 150000, TV_TELA_AMOLED, 500);

        Response response =  RestAssured.given()
                .queryParam(PRECO, 150000)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
                .extract().response();

        validarProduto(response, id);
        deletProdutos(id);
    }

    @Test
    public void campoPrecoVazioQuery() {
        RestAssured.given()
                .queryParam(PRECO, "")
                .when()
                .get()
                .then()
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(PRECO, equalTo(PRECO_DEVE_SER_POSITIVO))
        ;

    }

    @ParameterizedTest
    @MethodSource("precoInvalidos")
    public void campoPrecoInvalidoQuery(Object precoInvalidos) {
        RestAssured.given()
                .queryParam(PRECO, precoInvalidos)
                .when()
                .get()
                .then()
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

        Response response = RestAssured.given()
                .queryParam(DESCRICAO, TV_TELA_AMOLED)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
                .extract().response();
        ;
        validarProduto(response, id);
        deletProdutos(id);
    }

    @Test
    public void campoDescricaoInexistenteQuey() {
        RestAssured.given()
                .queryParam(DESCRICAO, TV_SAMSUNGPRIME_60)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
                .body(QUANTIDADE, equalTo(NUMERAL_ZERO))
                .body(PRODUTOS, empty())
        ;
    }

    @Test
    public void campoDescricaoVazioQuery() {
        RestAssured.given()
                .queryParam(DESCRICAO, "")
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
        ;
    }

    @Test
    public void campoQuantidadeValidoQuery() {
        String id = postProdutosAll(TV_SAMSUNG_60, 150000, TV_TELA_AMOLED, 500);
        Response response =  RestAssured.given()
                .queryParam(QUANTIDADE, 500)
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
                .extract().response();

        validarProduto(response, id);
        deletProdutos(id);
    }

    @Test
    public void campoQuatidadeInvalidaQuery() {
        RestAssured.given()
                .queryParam(QUANTIDADE, -200)
                .when()
                .get()
                .then()
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(QUANTIDADE, equalTo(QUANTIDADE_MAIOR_IGUAL_A_ZERO))
        ;
    }

    @Test
    public void campoQuantidadeVazioQuery() {
        RestAssured.given()
                .queryParam(QUANTIDADE, "")
                .when()
                .get()
                .then()
                .statusCode(SC_OK).log().all()
        ;
    }
}