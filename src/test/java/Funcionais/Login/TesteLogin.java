package Funcionais.Login;

import core.ObjetosLogin;

import org.junit.jupiter.api.Test;

import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TesteLogin extends BaseTest {
    @Test
    public void deveRealizarLoginComSucesso() {
        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(FULANO_QA);
        objetosLogin.setPassword(TESTE);

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
        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(FULANO_QA);
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
        ObjetosLogin objetosLogin = new ObjetosLogin();
        objetosLogin.setEmail(FULANO_QA);
        objetosLogin.setPassword("");

        loginServerRest.postLogin(objetosLogin)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(PASWORD, equalTo(PASSWORD_NAO_PODE_FICA_EM_BBRANCO));
    }
}
