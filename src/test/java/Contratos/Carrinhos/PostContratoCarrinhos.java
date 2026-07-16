package Contratos.Carrinhos;

import Carrinhos.BaseTest;
import Utils.SchemaPaths;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.ObjetosCarrinhos;
import core.ObjetosCarrinhosLista;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static Utils.TestesUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.*;

public class PostContratoCarrinhos extends BaseTest {
    @Test
    public void PostCotratoCarrinhos() throws JsonProcessingException {

        String id = postProdutos();

        ObjetosCarrinhos objetosCarrinhos = new ObjetosCarrinhos();
        objetosCarrinhos.setQuantidade(1);
        objetosCarrinhos.setIdProduto(id);

        List<ObjetosCarrinhos> produtos = new ArrayList<>();
        produtos.add(objetosCarrinhos);

        ObjetosCarrinhosLista objetosCarrinhosLista = new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        File jsonSchema = new File(SchemaPaths.POST_CARRINHOS_SCHEMA);

        RestAssured.given()
                .when()
                .body(json)
                .post(APP_BASE_PATH_CARRINHOS)
                .then()
                .statusCode(SC_CREATED)
                .body(matchesJsonSchema(jsonSchema))
        ;
        deletCarrinho(TOKEN);
        deletProdutos(id);
        deletUsuario(idUsuario);


    }
}
