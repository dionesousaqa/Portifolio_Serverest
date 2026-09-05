package Componentes.Login;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class LoginServerRest {
    public ValidatableResponse postLogin(Object objetosLogin){

        return   RestAssured.given()
                .baseUri("https://serverest.dev")
                .contentType(ContentType.JSON)
                .body(objetosLogin)
                .when()
                .post("/login")
                .then()
                ;
    }
}
