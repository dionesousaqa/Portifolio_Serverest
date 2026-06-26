package Utils;

import Servicos.Autenticacao;
import core.ObjetosProdutos;
import core.ObjetosUsuarios;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Optional;

import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestesUtils {
    public static void deletUsuario(String _id) {
        RestAssured.delete(_id).then().statusCode(200);
    }

    public static String postUsuario(String email) {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("TesteSeguranca");

        Response response = RestAssured.given()
                .header(AUTHORIZATION, Autenticacao.tokenBearer(getUsuariosLogin()))
                .body(objetosUsuarios)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().response();
        return response.path("_id");
    }

    public static void deletProdutos(String _id) {
        Autenticacao autenticacao = new Autenticacao();
        RestAssured.given()
                .header("Authorization", Autenticacao.tokenBearer(getUsuariosLogin()))
                .when()
                .delete(_id)
                .then().statusCode(SC_OK);
        ;
    }

    public static String postProdutos() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue 60");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        Response response = RestAssured.given()
                .header("Authorization", Autenticacao.tokenBearer(getUsuariosLogin()))
                .body(objetosProdutos)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().response();

        return response.path("_id");

    }

    public static String postProdutosAll(String nome, Integer preco, String descricao, Integer quantidade) {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(nome);
        objetosProdutos.setPreco(preco);
        objetosProdutos.setDescricao(descricao);
        objetosProdutos.setQuantidade(quantidade);

        Response response = RestAssured.given()
                .header("Authorization", Autenticacao.tokenBearer(getUsuariosLogin()))
                .body(objetosProdutos)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().response();

        return response.path("_id");

    }

    public static String getProdutos() {

        Response response = RestAssured.given()
                .when()
                .get()
                .then()
                .extract().response();
        return response.path("produtos.nome[0]");
    }

    public static Response getUsuariosLogin() {

        Response response = RestAssured.given()
                .queryParam("administrador", true)
                .when()
                .get("https://serverest.dev/usuarios")
                .then()
                .extract().response();
        return response;
    }

    public static void validarProduto(Response response, String id) {

        assertAll(
                () -> assertEquals(TV_SAMSUNG_60, response.path(PRODUTOS_NOME_ID_PRIMEIRO)),
                () -> assertEquals(150000, response.jsonPath().getInt((PRODUTOS_PRECO_ID_PRIMEIRO))),
                () -> assertEquals(TV_TELA_AMOLED, response.path(PRODUTOS_DESCRICAO_ID_PRIMEIRO)),
                () -> assertEquals(500, response.jsonPath().getInt((PRODUTO_QUANTIDADE_ID_PRIMEIRO))),
                () -> assertEquals(id, response.path(PRODUTO_ID_PRIMEIRO))
        );
    }
    public static void validarProdutosPath(Response response, String id){
        assertAll(
                () -> assertEquals(TV_SAMSUNG_60, response.path(NOME)),
                () -> assertEquals(150000,response.jsonPath().getInt((PRECO))),
                () -> assertEquals(TV_TELA_AMOLED,response.path(DESCRICAO)),
                () -> assertEquals(500,response.jsonPath().getInt(QUANTIDADE))

        );
    }
}
