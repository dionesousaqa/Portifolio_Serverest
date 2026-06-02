package Produtos.PostProdutos;

import Produtos.core.BaseTest;

import core.ObjetosProdutos;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static Utils.TestesUtils.*;
import static Utils.Utilitarios.*;

import static org.apache.http.HttpStatus.*;



public class PostProduto extends BaseTest {
    @Test
    public void creatProduto() {

        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tv Samsungue20300 60");
        objetosProdutos.setPreco(150000);
        objetosProdutos.setDescricao("Tv, tela Amoled");
        objetosProdutos.setQuantidade(500);

        Response response =
                RestAssured.given()
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .body(objetosProdutos)
                        .when()
                        .post()
                        .then()
                        .statusCode(SC_CREATED).extract().response();

        deletProdutos(response.path(ID));
    }

    @Test
    public void campoNomeExistente() {

        ObjetosProdutos objetosProdutosN = new ObjetosProdutos();
        objetosProdutosN.setNome(getProdutos());
        objetosProdutosN.setPreco(150000);
        objetosProdutosN.setDescricao("Tv, tela Amoled");
        objetosProdutosN.setQuantidade(500);

        Response responses =
                RestAssured.given()
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .body(objetosProdutosN)
                        .when()
                        .post()
                        .then()
                        .statusCode(SC_BAD_REQUEST).body(MESSAGE, Matchers.is("Já existe produto com esse nome")).extract().response();

    }
    @Test
    public void valorPrecoInvalido() {
        ObjetosProdutos objetosProdutosP = new ObjetosProdutos();
        objetosProdutosP.setNome(getProdutos());
        objetosProdutosP.setPreco(0000);
        objetosProdutosP.setDescricao("TV, tela Amoled");
        objetosProdutosP.setQuantidade(200);

                RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION,TOKEN)
                .body(objetosProdutosP)
                .when()
                .post()
                .then()
                .statusCode(SC_BAD_REQUEST).body(PRECO, Matchers.is( "preco deve ser um número positivo"));
    }
    @Test
    public void valorDescricaoExistente(){
        ObjetosProdutos objetosProdutosD = new ObjetosProdutos();
        objetosProdutosD.setNome("TVZ Sampitoxibaxx");
        objetosProdutosD.setPreco(1500);
        objetosProdutosD.setDescricao("Mouse");
        objetosProdutosD.setQuantidade(200);

        Response responseD = RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutosD)
                .when()
                .post()
                .then()
                .statusCode(SC_CREATED).body(MESSAGE, Matchers.is( "Cadastro realizado com sucesso")).extract().response();

        deletProdutos(responseD.path(ID));
    }
    @Test
    public void valorQuantidadeInvalido(){
        ObjetosProdutos objetosProdutosQ = new ObjetosProdutos();
        objetosProdutosQ.setNome("TVs Samptoxchiba");
        objetosProdutosQ.setPreco(1500);
        objetosProdutosQ.setDescricao("Televisao");
        objetosProdutosQ.setQuantidade(-300);

                RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutosQ)
                .when()
                .post()
                .then()
                .statusCode(SC_BAD_REQUEST).body(QUANTIDADE, Matchers.is("quantidade deve ser maior ou igual a 0"));


    }
    @Test
    public void campoNomeVazio(){
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setPreco(1500);
        objetosProdutos.setDescricao("Televisao");
        objetosProdutos.setQuantidade(300);

                RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutos)
                .when()
                .post()
                .then()
               .statusCode(SC_BAD_REQUEST).body(NOME,Matchers.is("nome deve ser uma string"));


    }
    @Test
    public void campoPrecoVazio(){
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tevê SamptosShiba");
        objetosProdutos.setDescricao("Televisao");
        objetosProdutos.setQuantidade(300);

                RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .body(objetosProdutos)
                .when()
                .post()
                .then()
                .statusCode(SC_BAD_REQUEST).body(PRECO, Matchers.is("preco deve ser um número"));

    }
    @Test
    public void campoDescricaoVazio(){
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tev Samsungue");
        objetosProdutos.setPreco(1700);
        objetosProdutos.setQuantidade(500);

                RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION ,TOKEN)
                .body(objetosProdutos)
                .when()
                .post()
                .then()
                .statusCode(SC_BAD_REQUEST).body(DESCRICAO , Matchers.is("descricao deve ser uma string"));


    }
    @Test
    public void campoQuantidadeVazio(){
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("Tevê Dell");
        objetosProdutos.setPreco(1800);
        objetosProdutos.setDescricao("Televisao");

                 RestAssured.given()
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION ,TOKEN)
                .body(objetosProdutos)
                .when()
                .post()
                .then()
                .statusCode(SC_BAD_REQUEST).body( QUANTIDADE, Matchers.is("quantidade deve ser um número"));


    }
}
