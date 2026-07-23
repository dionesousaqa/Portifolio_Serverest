package Componentes.Usuarios;

import io.restassured.RestAssured;

import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static Utils.Utils.APP_BASE_PATH;

public class UsuariosServerRest {
    public ValidatableResponse deletUsuario(String _id) {
        return RestAssured.given()
                .when().log().all()
                .delete("usuarios/" + _id)
                .then();
    }

    public ValidatableResponse postUsuario(ObjetosUsuarios body) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH)
                .body(body)
                .when().log().all()
                .post()
                .then();
    }

    public ValidatableResponse getUsuarioPath() {
        return RestAssured.given()
                .basePath(APP_BASE_PATH)
                .when()
                .get()
                .then()
                ;
    }
    public ValidatableResponse getUsuarioPathId(String id) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH)
                .when()
                .get(id)
                .then()
                ;
    }

    public ValidatableResponse getUsuarioQuery(Map<String, Object> queryParams) {
        return RestAssured.given()
                .queryParams(queryParams)
                .basePath(APP_BASE_PATH)
                .when().log().all()
                .get()
                .then()
                ;
    }
    public ValidatableResponse putUsuario(String id, ObjetosUsuarios objetosUsuarios) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH)
                .body(objetosUsuarios)
                .when().log().all()
                .put(id)
                .then();
    }
    public ValidatableResponse delUsuario(String id) {
        return RestAssured.given()
                .basePath(APP_BASE_PATH)
                .when().log().all()
                .delete(id)
                .then();
    }
}