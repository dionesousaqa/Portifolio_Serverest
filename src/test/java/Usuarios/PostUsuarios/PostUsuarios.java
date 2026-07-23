package Usuarios.PostUsuarios;

import Componentes.Usuarios.ObjetosUsuarios;
import core.BaseTest;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.postUsuario;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;

public class PostUsuarios extends BaseTest {
    @Test
    public void deveInserirUsuarioComSucesso() {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail(gerarEmailUnico());
        objetosUsuarios.setNome(NOME_USUARIO_AP);
        objetosUsuarios.setPassword(PSWD);
        Response response = usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_CREATED).log().all()
                .body(MESSAGE, Matchers.is(CADASTRO_REALIZADO))
                .extract().response();
        usuariosServerRest.deletUsuario(response.path(ID));

    }

    @Test
    public void validandoCampoAdministradorVazio() {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador("");
        objetosUsuarios.setEmail(EMAIL_ATT);
        objetosUsuarios.setNome(NOME_USUARIO_AP);
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(ADMIN, Matchers.is(ADMIN_TRUE_OR_FALSE))
        ;
    }

    @Test
    public void validandoCampoEmailVazio() {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail("");
        objetosUsuarios.setNome(NOME_USUARIO_AP);
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(EMAIL, Matchers.is(EMAIL_NAO_PODE_FICAR_EM_BRANCO))
        ;
    }

    @Test
    public void validandoCampoNomeVazio() {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail(EMAIL_ATT);
        objetosUsuarios.setNome("");
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(NOME, Matchers.is(NOME_NAO_PODE_FICAR_EM_BRANCO))
        ;
    }

    @Test
    public void validandoCampoPasswordVazio() {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail(EMAIL_ATT);
        objetosUsuarios.setNome(NOME_USUARIO_AP);
        objetosUsuarios.setPassword("");

        usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(PASSWORD, Matchers.is(PSWD_NAO_PODE_FICAR_EM_BRANCO))
        ;
    }

    @Test
    public void validandoCampoEmailInvalido() {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail(EMAIL_INVALIDO);
        objetosUsuarios.setNome(NOME_USUARIO_AP);
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(EMAIL, Matchers.is(EMAIL_DEVE_SER_VALIDO))
        ;
    }

    @Test
    public void validandoCampoEmailExistente() {
        String id = postUsuario(EMAIL_ATP);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail(EMAIL_ATP);
        objetosUsuarios.setNome(NOME_USUARIO_AP);
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_BAD_REQUEST).log().all()
                .body(MESSAGE, Matchers.is(EMAIL_JA_USADO))
        ;
        usuariosServerRest.deletUsuario(id);
    }
}
