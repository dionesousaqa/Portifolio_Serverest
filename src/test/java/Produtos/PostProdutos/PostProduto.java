package Produtos.PostProdutos;


import Componentes.Produtos.ObjetosProdutos;
import Produtos.core.BaseTest;
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
        objetosProdutos.setNome(TV_SAMSUNG_90);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        Response response =
                produtosServerRest.postProdutos(objetosProdutos)
                        .statusCode(SC_CREATED).extract().response();

        produtosServerRest.delProdutos(response.path(ID));
    }

    @Test
    public void campoNomeExistente() {

        ObjetosProdutos objetosProdutosN = new ObjetosProdutos();
        objetosProdutosN.setNome(getProdutos());
        objetosProdutosN.setPreco(PRECO_PRODUTO);
        objetosProdutosN.setDescricao(TV_TELA_AMOLED);
        objetosProdutosN.setQuantidade(QUANTIDADE_PRODUTO);

        produtosServerRest.postProdutos(objetosProdutosN)
                .statusCode(SC_BAD_REQUEST).body(MESSAGE, Matchers.is(JA_EXISTE_PRODUTO_COM_ESSE_NOME))
                .extract().response();

    }
    @Test
    public void valorPrecoInvalido() {
        ObjetosProdutos objetosProdutosP = new ObjetosProdutos();
        objetosProdutosP.setNome(getProdutos());
        objetosProdutosP.setPreco(0000);
        objetosProdutosP.setDescricao(TV_TELA_AMOLED);
        objetosProdutosP.setQuantidade(QUANTIDADE_PRODUTO);

        produtosServerRest.postProdutos(objetosProdutosP)
                .statusCode(SC_BAD_REQUEST).body(PRECO, Matchers.is(PRECO_DEVE_SER_UM_NUMERO_POSITIVO));
    }

    @Test
    public void valorDescricaoExistente() {
        ObjetosProdutos objetosProdutosD = new ObjetosProdutos();
        objetosProdutosD.setNome(TV_SAMSUNG_90);
        objetosProdutosD.setPreco(PRECO_PRODUTO);
        objetosProdutosD.setDescricao(MOUSE);
        objetosProdutosD.setQuantidade(QUANTIDADE_PRODUTO);

        Response responseD =
                produtosServerRest.postProdutos(objetosProdutosD)
                        .statusCode(SC_CREATED).body(MESSAGE, Matchers.is(CADASTRO_REALIZADO)).extract().response();

        produtosServerRest.delProdutos(responseD.path(ID));

    }

    @Test
    public void valorQuantidadeInvalido() {
        ObjetosProdutos objetosProdutosQ = new ObjetosProdutos();
        objetosProdutosQ.setNome(TV_SAMSUNG_90);
        objetosProdutosQ.setPreco(PRECO_PRODUTO);
        objetosProdutosQ.setDescricao(MOUSE);
        objetosProdutosQ.setQuantidade(-300);

        produtosServerRest.postProdutos(objetosProdutosQ)
                .statusCode(SC_BAD_REQUEST).body(QUANTIDADE, Matchers.is(QUANTIDADE_MAIOR_IGUAL_A_ZERO));
    }

    @Test
    public void campoNomeVazio() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(MOUSE);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);
        produtosServerRest.postProdutos(objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(NOME, Matchers.is( NOME_DEVE_SER_STRING));
    }

    @Test
    public void campoPrecoVazio() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_90);
        objetosProdutos.setDescricao(MOUSE);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        produtosServerRest.postProdutos(objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(PRECO, Matchers.is( PRECO_DEVE_SER_NUMERO));
    }

    @Test
    public void campoDescricaoVazio() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_90);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);
        produtosServerRest.postProdutos(objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(DESCRICAO, Matchers.is(DESCRICAO_DEVE_SER_STRING));
    }

    @Test
    public void campoQuantidadeVazio() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_90);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(MOUSE);

        produtosServerRest.postProdutos(objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(QUANTIDADE, Matchers.is(QUANTIDADE_DEVE_SER_NUMERO));
    }
}
