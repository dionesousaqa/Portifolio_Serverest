package Componentes.Produtos;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.util.Map;
import static Utils.Utils.APP_BASE_PATH_PRODUTOS;

public class ProdutosServerRest {
    public ValidatableResponse postProdutos(ObjetosProdutos body) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_PRODUTOS)
                .body(body)
                .when()
                .post()
                .then();
    }

    public ValidatableResponse delProdutos(String id) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_PRODUTOS)
                .when()
                .delete(id)
                .then()
                ;
    }

    public ValidatableResponse getProdutosPath() {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_PRODUTOS)
                .when()
                .get()
                .then()
                ;
    }
    public ValidatableResponse getProdutosPathId(String id) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_PRODUTOS)
                .when()
                .get(id)
                .then()
                ;
    }
    public ValidatableResponse getProdutosQuery(Map<String, Object> queryParams) {
        return RestAssured.given()
                .queryParams(queryParams)
                .basePath(APP_BASE_PATH_PRODUTOS)
                .when().log().all()
                .get()
                .then()
                ;
    }
    public ValidatableResponse putProdutos(String id, ObjetosProdutos objetosProdutos) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_PRODUTOS)
                .body(objetosProdutos)
                .when().log().all()
                .put(id)
                .then();
    }
}