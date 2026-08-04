package Funcionais.Carrinhos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.ObjetosCarrinhos;
import core.ObjetosCarrinhosLista;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Utils.TestesUtils.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class EndToEndCancelarCompras extends BaseTest{
    @Test
    public void e2ECarrinhoCancelCompras() throws JsonProcessingException {
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

        Response response = carrinhoServerRest.postCarrinhos(json)
                .statusCode(SC_CREATED)
                .extract()
                .response();

        String idCarrinho = response.path("_id");

        carrinhoServerRest.getCarrinhoPathId(idCarrinho)
                .statusCode(SC_OK).log().all();

        produtoServiceRest.getProdutosPathId(id).log().all()
                .body("quantidade", Matchers.is(499));

        carrinhoServerRest.delCarrinhosCancellCompras();

        produtoServiceRest.getProdutosPathId(id).log().all()
                .body("quantidade", Matchers.is(500));

        deletProdutos(id);
        deletUsuario(idUsuario);

    }
}

