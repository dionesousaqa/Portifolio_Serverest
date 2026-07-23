package Produtos.EndToEnd;

import Componentes.Produtos.ObjetosProdutos;
import Produtos.core.BaseTest;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static Utils.TestesUtils.*;
import static Utils.Utilitarios.*;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class TesteEndToEnd extends BaseTest {
    @Test
    public void postProdutosE2E() {

        String id = postProdutos();
        produtosServerRest.getProdutosQuery(Map.of(ID, id))
                .statusCode(SC_OK)
                .body(QUANTIDADE, equalTo(NUMERO_UM))
                .body(PRODUTO_ID_PRIMEIRO, equalTo(id))
        ;
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_90);
        objetosProdutos.setPreco(NUMERO_QUINZE);
        objetosProdutos.setDescricao(TV_TELA_OLED);
        objetosProdutos.setQuantidade(NUMERO_TRES);

        produtosServerRest.putProdutos(id, objetosProdutos)
                .statusCode(SC_OK).body(MESSAGE, equalTo(REGISTRO_ALTERADO));
        ;
        produtosServerRest.getProdutosPathId(id)
                .statusCode(SC_OK)
                .body(NOME, equalTo(TV_SAMSUNG_90))
                .body(PRECO, equalTo(NUMERO_QUINZE))
                .body(DESCRICAO, equalTo(TV_TELA_OLED))
                .body(QUANTIDADE, equalTo(NUMERO_TRES))
                .body(ID, equalTo(id))
        ;
        produtosServerRest.delProdutos(id);
    }
}
