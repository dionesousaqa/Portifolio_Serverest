package Produtos.PutProdutos;


import Produtos.core.BaseTest;
import core.ObjetosProdutos;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static Utils.TestesUtils.deletProdutos;
import static Utils.TestesUtils.postProdutos;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class PutProdutosTest extends BaseTest {
    @Test
    public void alterarNome() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv LG 60 ALTERADA");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);
        String id = postProdutos();

        RestAssured.given()
                .body(objetosProdutos)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .put(id)
                .then()
                .statusCode(SC_OK).body("message", equalTo("Registro alterado com sucesso"));

        deletProdutos(id);
    }

    @Test
    public void alterarPreco() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 60");
        objetosProdutos.setPreco(12000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        String id = postProdutos();

        RestAssured.given()
                .body(objetosProdutos)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .put(id)
                .then()
                .statusCode(SC_OK).body("message", equalTo("Registro alterado com sucesso"));

        deletProdutos(id);
    }

    @Test
    public void alterarDescricao() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 60");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela FullHD");
        objetosProdutos.setQuantidade(500);

        String id = postProdutos();

        RestAssured.given()
                .body(objetosProdutos)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .put(id)
                .then()
                .statusCode(SC_OK).body("message", equalTo("Registro alterado com sucesso"));

        deletProdutos(id);
    }

    @Test
    public void alterarQuantidade() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 60");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(400);

        String id = postProdutos();
        RestAssured.given()
                .body(objetosProdutos)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .put(id)
                .then()
                .statusCode(SC_OK).body("message", equalTo("Registro alterado com sucesso"));

        deletProdutos(id);
    }

    @Test
    public void editarCadastroInexistente() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 900");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        Response response =

                RestAssured.given()
                        .body(objetosProdutos)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .when()
                        .put("1010201482141010")
                        .then()
                        .statusCode(SC_CREATED).body("message", equalTo("Cadastro realizado com sucesso"))
                        .extract().response();

        deletProdutos(response.path("_id"));
    }

    @Test
    public void validarIdInvalido() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 900");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        RestAssured.given()
                .body(objetosProdutos)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .put("10102014821410100")
                .then()
                .statusCode(SC_BAD_REQUEST).body("id", equalTo("id deve ter exatamente 16 caracteres alfanuméricos"));
    }

    @ParameterizedTest
    @MethodSource("idsInvalidos")
    public void validarIdInvalidoParametrizado(String idsInvalidos) {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 900");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        RestAssured.given()
                .body(objetosProdutos)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .when()
                .put(idsInvalidos)
                .then()
                .statusCode(SC_BAD_REQUEST).body("id", equalTo("id deve ter exatamente 16 caracteres alfanuméricos"));

    }
    static Stream<Arguments> idsInvalidos() {
        return Stream.of(Arguments.of("101020148214101"),
                Arguments.of("10102014821410100"));
    }

    @Test
    public void campoNomeEmBranco() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        String id = postProdutos();

        RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutos)
                .when()
                .put(id)
                .then()
                .statusCode(SC_BAD_REQUEST).body("nome", equalTo("nome não pode ficar em branco"));
        deletProdutos(id);

    }

    @Test
    public void campoPrecoInvalido() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 60");
        objetosProdutos.setPreco(null);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        String id = postProdutos();

        RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutos)
                .when()
                .put(id)
                .then()
                .statusCode(SC_BAD_REQUEST).body("preco", equalTo("preco deve ser um número"));
        deletProdutos(id);
    }

    @Test
    public void campoDescricaoEmBranco() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 60");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("");
        objetosProdutos.setQuantidade(500);

        String id = postProdutos();

        RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutos)
                .when()
                .put(id)
                .then()
                .statusCode(SC_BAD_REQUEST).body("descricao", equalTo("descricao não pode ficar em branco"));
        deletProdutos(id);
    }

    @Test
    public void campoQuantidadeInvalido() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 60");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(null);

        String id = postProdutos();

        RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutos)
                .when()
                .put(id)
                .then()
                .statusCode(SC_BAD_REQUEST).body("quantidade", equalTo("quantidade deve ser um número"));
        deletProdutos(id);
    }
}