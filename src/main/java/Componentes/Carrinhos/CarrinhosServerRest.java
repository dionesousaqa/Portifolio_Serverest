package Componentes.Carrinhos;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import java.util.Map;
import static Utils.Utils.APP_BASE_PATH_CARRINHOS;


public class CarrinhosServerRest {
    public ValidatableResponse postCarrinhos(String body){
        return RestAssured.given()
                .basePath(APP_BASE_PATH_CARRINHOS)
                .body(body)
                .when()
                .post()
                .then()
            ;
}
    public ValidatableResponse delCarrinhosCancellCompras() {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_CARRINHOS+"/cancelar-compra")
                .when()
                .delete()
                .then()
                ;
    }
    public ValidatableResponse delCarrinhosConcluCompras() {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_CARRINHOS + "/concluir-compra")
                .when()
                .delete()
                .then()
                ;
    }
    public ValidatableResponse getCrrinhosQuery(Map<String, Object> queryParams) {
        return RestAssured.given()
                .queryParams(queryParams)
                .basePath(APP_BASE_PATH_CARRINHOS)
                .when().log().all()
                .get()
                .then()
                ;
    }
    public ValidatableResponse getCarrinhoPathId(String id) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_CARRINHOS)
                .when()
                .get(id)
                .then()
                ;
    }
    public ValidatableResponse getCarrinhoPath() {
        return RestAssured.given()
                .basePath(APP_BASE_PATH_CARRINHOS)
                .when()
                .get()
                .then()
                ;
    }
}
