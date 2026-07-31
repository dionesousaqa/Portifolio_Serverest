package Funcionais.Carrinhos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.ObjetosCarrinhos;
import core.ObjetosCarrinhosLista;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Utils.TestesUtils.*;
import static org.apache.http.HttpStatus.*;

public class PostCarrinhos extends BaseTest {
    private static final Log log = LogFactory.getLog(PostCarrinhos.class);

    @Test
    public void postCarrinhosIdProdutoValido() throws JsonProcessingException {
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

        carrinhoServerRest.postCarrinhos(json).log().all()
        ;
        deletCarrinho(TOKEN);
        deletProdutos(id);
        deletUsuario(idUsuario);
    }

    @Test
    public void postCarrinhosIdProdutosInvalido() throws JsonProcessingException {

        ObjetosCarrinhos objetosCarrinhos = new ObjetosCarrinhos();
        objetosCarrinhos.setQuantidade(1);
        objetosCarrinhos.setIdProduto("vCbII5BkohrzXaYF");

        List<ObjetosCarrinhos> produtos = new ArrayList<>();
        produtos.add(objetosCarrinhos);

        ObjetosCarrinhosLista objetosCarrinhosLista = new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        carrinhoServerRest.postCarrinhos(json).log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.is("Produto não encontrado"));
        ;

        deletUsuario(idUsuario);
    }

    @Test
    public void postCarrinhosIdProdutosVazio() throws JsonProcessingException {

        ObjetosCarrinhos objetosCarrinhos = new ObjetosCarrinhos();
        objetosCarrinhos.setQuantidade(1);
        objetosCarrinhos.setIdProduto("");

        List<ObjetosCarrinhos> produtos = new ArrayList<>();
        produtos.add(objetosCarrinhos);

        ObjetosCarrinhosLista objetosCarrinhosLista = new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        carrinhoServerRest.postCarrinhos(json).log().all()
                .statusCode(SC_BAD_REQUEST).log().all()
                .body("'produtos[0].idProduto'", Matchers.is("produtos[0].idProduto não pode ficar em branco"))
                .body("produtos", Matchers.is("produtos não contém 1 valor obrigatório"));
        ;

        deletUsuario(idUsuario);
    }

    @Test
    public void postCarrinhosQuantidadeInvalida() throws JsonProcessingException {
        String id = postProdutos();

        ObjetosCarrinhos objetosCarrinhos = new ObjetosCarrinhos();
        objetosCarrinhos.setQuantidade(-1);
        objetosCarrinhos.setIdProduto(id);

        List<ObjetosCarrinhos> produtos = new ArrayList<>();
        produtos.add(objetosCarrinhos);

        ObjetosCarrinhosLista objetosCarrinhosLista = new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        carrinhoServerRest.postCarrinhos(json).log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("'produtos[0].quantidade'", Matchers.is("produtos[0].quantidade deve ser um número positivo"))
                .body("produtos", Matchers.is("produtos não contém 1 valor obrigatório"));
        ;

        deletCarrinho(TOKEN);
        deletProdutos(id);
        deletUsuario(idUsuario);
    }

    @Test
    public void postAddProdutosNoCarrinho() throws JsonProcessingException {
        String id = postProdutos();
        String id2 = postProdutosAll("TV LG", 500, "Televisão", 2);

        ObjetosCarrinhos objetosCarrinhos = new ObjetosCarrinhos();
        objetosCarrinhos.setQuantidade(1);
        objetosCarrinhos.setIdProduto(id);

        ObjetosCarrinhos objetosCarrinhos1 = new ObjetosCarrinhos();
        objetosCarrinhos1.setIdProduto(id2);
        objetosCarrinhos1.setQuantidade(2);

        List<ObjetosCarrinhos> produtos = new ArrayList<>();
        produtos.add(objetosCarrinhos);
        produtos.add(objetosCarrinhos1);

        ObjetosCarrinhosLista objetosCarrinhosLista = new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        carrinhoServerRest.postCarrinhos(json).log().all()
                .statusCode(SC_CREATED)
                .body("message", Matchers.is("Cadastro realizado com sucesso"))
        ;
        deletCarrinho(TOKEN);
        deletProdutos(id);
        deletProdutos(id2);
        deletUsuario(idUsuario);
    }

    @Test
    public void postCarrinhosListaSemProdutos() throws JsonProcessingException {

        List<ObjetosCarrinhos> produtos = new ArrayList<>();

        ObjetosCarrinhosLista objetosCarrinhosLista = new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        carrinhoServerRest.postCarrinhos(json).log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("produtos", Matchers.is("produtos não contém 1 valor obrigatório"));
        ;
        deletCarrinho(TOKEN);
        deletUsuario(idUsuario);
    }

    @Test
    public void postCarrinhosQuantidadeZerada() throws JsonProcessingException {
        String id = postProdutos();

        ObjetosCarrinhos objetosCarrinhos = new ObjetosCarrinhos();
        objetosCarrinhos.setQuantidade(0);
        objetosCarrinhos.setIdProduto(id);

        List<ObjetosCarrinhos> produtos = new ArrayList<>();
        produtos.add(objetosCarrinhos);

        ObjetosCarrinhosLista objetosCarrinhosLista = new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        carrinhoServerRest.postCarrinhos(json).log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("'produtos[0].quantidade'", Matchers.is("produtos[0].quantidade deve ser um número positivo"))
                .body("produtos", Matchers.is("produtos não contém 1 valor obrigatório"));

        deletCarrinho(TOKEN);
        deletProdutos(id);
        deletUsuario(idUsuario);
    }
    @Test
    public void postCarrinhoQuantidadeNula() throws JsonProcessingException {
        String id = postProdutos();

        ObjetosCarrinhos objetosCarrinhos = new ObjetosCarrinhos();
        objetosCarrinhos.setQuantidade(null);
        objetosCarrinhos.setIdProduto(id);

        List<ObjetosCarrinhos> produtos = new ArrayList<>();
        produtos.add(objetosCarrinhos);

        ObjetosCarrinhosLista objetosCarrinhosLista= new ObjetosCarrinhosLista();
        objetosCarrinhosLista.setProdutos(produtos);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objetosCarrinhosLista);

        System.out.println(json);

        carrinhoServerRest.postCarrinhos(json)
        ;
        deletCarrinho(TOKEN);
        deletProdutos(id);
        deletUsuario(idUsuario);

    }
}