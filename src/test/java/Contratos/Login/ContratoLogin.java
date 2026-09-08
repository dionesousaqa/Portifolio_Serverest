package Contratos.Login;

import Funcionais.Login.BaseTest;
import Utils.SchemaPaths;
import Utils.TestesUtils;
import core.ObjetosLogin;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.Utilitarios.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class ContratoLogin extends BaseTest{
    @Test
    public void contratoLogin(){
        Response response = TestesUtils.getUsuariosLogin();

        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(response.path("usuarios.email[0]"));
        objetosLogin.setPassword(response.path("usuarios.password[0]"));

        File jsonSchema = new File(SchemaPaths.CONTRATO_LOGIN);

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_OK).log().all()
                .body(matchesJsonSchema(jsonSchema))
                .body(MESSAGE, equalTo(LOGIN_SUCCESSFUL))
                .body(AUTHORIZATION, notNullValue());
    }
}

