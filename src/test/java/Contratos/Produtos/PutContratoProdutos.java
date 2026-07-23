package Contratos.Produtos;

import Componentes.Produtos.ObjetosProdutos;
import Produtos.core.BaseTest;
import Utils.SchemaPaths;
import org.junit.jupiter.api.Test;

import java.io.File;
import static Utils.TestesUtils.postProdutos;
import static Utils.Utilitarios.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class PutContratoProdutos extends BaseTest {
    @Test
    public void putContratoProdutos() {
        String id = postProdutos();
        ObjetosProdutos objetosProdutos = new ObjetosProdutos();
        objetosProdutos.setNome(TV_SAMSUNG_90 );
        objetosProdutos.setPreco(PRECO_PRODUTO);
        objetosProdutos.setDescricao(TV);
        objetosProdutos.setQuantidade(QUANTIDADE_PRODUTO);

        File jsonSchema = new File(SchemaPaths.PUT_PRODUTOS_SCHEMA);
        produtosServerRest.putProdutos(id,objetosProdutos)
                 .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema));
        produtosServerRest.delProdutos(id);
    }
}
