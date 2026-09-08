package Funcionais.Login;

import Utils.TestesUtils;
import core.ObjetosLogin;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TesteLogin extends BaseTest {
    @Test
    public void deveRealizarLoginComSucesso() {
        Response response = TestesUtils.getUsuariosLogin();

        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(response.path("usuarios.email[0]"));
        objetosLogin.setPassword(response.path("usuarios.password[0]"));

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_OK).log().all()
                .body(MESSAGE, equalTo(LOGIN_SUCCESSFUL))
                .body(AUTHORIZATION, notNullValue());
    }

    @Test
    public void deveRetornarErroDeEmailInvalido() {
        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(EMAIL_INVALIDO);
        objetosLogin.setPassword(TESTE);

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(EMAIL, equalTo(EMAIL_DEVE_SER_VALIDO))
        ;
    }

    @Test
    public void deveRetornarErroDeSenhaInvalida() {
        Response response = TestesUtils.getUsuariosLogin();

        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(response.path("usuarios.email[0]"));
        objetosLogin.setPassword(PSWD_INVALID);

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_UNAUTHORIZED).log().all()
                .body(MESSAGE, equalTo(EMAIL_SENHA_INVALIDO))
        ;
    }

    @Test
    public void deveRetorarErroDeEmailVazio() {
        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail("");
        objetosLogin.setPassword(TESTE);

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(EMAIL, equalTo(EMAIL_NAO_PODE_FICAR_EM_BRANCO))

        ;
    }

    @Test
    public void deveRetornarErroDePasswordVazio() {
        Response response = TestesUtils.getUsuariosLogin();

        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(response.path("usuarios.email[0]"));
        objetosLogin.setPassword("");

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(PASWORD, equalTo(PASSWORD_NAO_PODE_FICA_EM_BBRANCO));
    }
}
