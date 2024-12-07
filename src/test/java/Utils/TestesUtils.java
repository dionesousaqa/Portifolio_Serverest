package Utils;

import core.ObjetosUsuarios;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestesUtils {
    public static void deletUsuario(String _id) {
        RestAssured.delete(_id).then().statusCode(200);
    }
    public static String postUsuario(String email) {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("true");
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setNome("Antonio Pereira");
        objetosUsuarios.setPassword("TesteSeguranca");

      Response response= RestAssured.given()
                   .body(objetosUsuarios)
                .when()
                   .post()
                .then()
                   .statusCode(201)
                   .extract().response()

        ;
      return response.path("_id");
    }

}
