package Produtos.PutProdutos;


import Componentes.Produtos.ObjetosProdutos;
import Produtos.core.BaseTest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static Utils.TestesUtils.postProdutos;

import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class PutProdutosTest extends BaseTest {
    @Test
    public void alterarNome() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_LG_ALTERADA);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);
        String id = postProdutos();

        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_OK).body(MESSAGE, equalTo(REGISTRO_ALTERADO));

        produtosServerRest.delProdutos(id);
    }

    @Test
    public void alterarPreco() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMUNGUE_60);
        objetosProdutos.setPreco(PRECO_PRODUTO_ALTERADO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        String id = postProdutos();
        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_OK).body(MESSAGE, equalTo(REGISTRO_ALTERADO));

        produtosServerRest.delProdutos(id);
    }

    @Test
    public void alterarDescricao() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMUNGUE_60);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_FULLHD);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        String id = postProdutos();
        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_OK).body(MESSAGE, equalTo(REGISTRO_ALTERADO));

        produtosServerRest.delProdutos(id);
    }

    @Test
    public void alterarQuantidade() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMUNGUE_60);
        objetosProdutos.setPreco(PRECO_PRODUTO_ALTERADO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO_ALTERADA);

        String id = postProdutos();
        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_OK).body(MESSAGE, equalTo(REGISTRO_ALTERADO));

        produtosServerRest.delProdutos(id);
    }

    @Test
    public void editarCadastroInexistente() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_70);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        Response response =
                produtosServerRest.putProdutos(ID_INEXISTENTE, objetosProdutos)
                        .statusCode(SC_CREATED).body(MESSAGE, equalTo(CADASTRO_REALIZADO))
                        .extract().response();

        produtosServerRest.delProdutos(response.path(ID));
    }

    @Test
    public void validarIdInvalido() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_70);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        produtosServerRest.putProdutos(ID_INCOMPLETO, objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(BODY_ID, equalTo(ID_DEVE_TER_EXATAMENTE_16_CARACTERES));
    }

    @ParameterizedTest
    @MethodSource("idsInvalidos")
    public void validarIdInvalidoParametrizado(String idsInvalidos) {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_90);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        produtosServerRest.putProdutos(idsInvalidos, objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(BODY_ID, equalTo(ID_DEVE_TER_EXATAMENTE_16_CARACTERES));
    }
    static Stream<Arguments> idsInvalidos() {
        return Stream.of(Arguments.of("101020148214101"),
                Arguments.of("10102014821410100"));
    }

    @Test
    public void campoNomeEmBranco() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome("");
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        String id = postProdutos();

        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(NOME, equalTo(NOME_NAO_PODE_FICAR_EM_BRANCO));

        produtosServerRest.delProdutos(id);

    }
    @Test
    public void campoPrecoInvalido() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMUNGUE_60);
        objetosProdutos.setPreco(null);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        String id = postProdutos();

        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(PRECO, equalTo(PRECO_DEVE_SER_UM_NUMERO));

        produtosServerRest.delProdutos(id);
    }

    @Test
    public void campoDescricaoEmBranco() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMUNGUE_60);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao("");
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        String id = postProdutos();

        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(DESCRICAO, equalTo(PRECO_NAO_PODE_FICAR_EM_BRACNO));

        produtosServerRest.delProdutos(id);
    }

    @Test
    public void campoQuantidadeInvalido() {
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMUNGUE_60);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV_TELA_AMOLED);
        objetosProdutos.setQuantidade(null);

        String id = postProdutos();

        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_BAD_REQUEST).body(QUANTIDADE, equalTo(QUANTIDADE_DEVE_SER_UM_NUMERO));
        produtosServerRest.delProdutos(id);
    }
}