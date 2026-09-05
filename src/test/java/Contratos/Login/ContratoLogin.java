package Contratos.Login;

import Funcionais.Login.BaseTest;
import Utils.SchemaPaths;
import core.ObjetosLogin;
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
        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(FULANO_QA);
        objetosLogin.setPassword(TESTE);

        File jsonSchema = new File(SchemaPaths.CONTRATO_LOGIN);

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_OK).log().all()
                .body(matchesJsonSchema(jsonSchema))
                .body(MESSAGE, equalTo(LOGIN_SUCCESSFUL))
                .body(AUTHORIZATION, notNullValue());
    }
}

