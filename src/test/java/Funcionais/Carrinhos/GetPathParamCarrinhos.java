package Funcionais.Carrinhos;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static Utils.TestesUtils.*;
import static Utils.TestesUtils.deletUsuario;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

public class GetPathParamCarrinhos extends BaseTest {
    @Test
    public void getPathCarrinhoIdValido(){
        String idProduto = postProdutosC();
        String id = postCarrinhos(idProduto);

        carrinhoServerRest.getCarrinhoPathId(id)
                .statusCode(SC_OK)
                ;
        deletCarrinho(TOKEN);
        deletProdutos(idProduto);
        deletUsuario(idUsuario);


    }
    @Test
    public void getPathCarrinhoIdInvalido(){
        carrinhoServerRest.getCarrinhoPathId("0025285859785487")
                .statusCode(SC_BAD_REQUEST).log().all()
                .body("message", Matchers.is("Carrinho não encontrado"))
                ;
        deletUsuario(idUsuario);
    }
    @Test
    public void getPathCarrinhoIdVazio(){

        carrinhoServerRest.getCarrinhoPathId("")
                    .log().all()
        ;
        deletUsuario(idUsuario);
    }
}
