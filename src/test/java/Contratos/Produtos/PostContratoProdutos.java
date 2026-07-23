package Contratos.Produtos;

import Componentes.Produtos.ObjetosProdutos;
import Produtos.core.BaseTest;
import Utils.SchemaPaths;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.Utilitarios.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.*;

public class PostContratoProdutos extends BaseTest {
    @Test
    public void postContratoProdutos(){
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_60);
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        File jsonSchema = new File(SchemaPaths.POST_PRODUTOS_SCHEMA);
        String id = produtosServerRest.postProdutos(objetosProdutos)
                .statusCode(SC_CREATED)
                .body(matchesJsonSchema(jsonSchema))
                .extract().path(ID)
        ;
        produtosServerRest.delProdutos(id);
    }
}
