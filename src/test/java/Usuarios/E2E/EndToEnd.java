package Usuarios.E2E;

import Componentes.Usuarios.ObjetosUsuarios;
import core.BaseTest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.*;


public class EndToEnd extends BaseTest {
    @Test
    public void validandoTesteEndToEnd() {
        String uniquePart = UUID.randomUUID().toString();
        String email = TESTE_USER + uniquePart + EXAMPLE_COM;

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setNome(NOME_USUARIO_AP);
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setPassword(PSWD);
        objetosUsuarios.setAdministrador(TRUE);

        String useID = usuariosServerRest.postUsuario(objetosUsuarios)
                .log().all()
                .statusCode(SC_CREATED)
                .extract().path(ID);

        usuariosServerRest.getUsuarioQuery(Map.of(ID, useID))
                .log().all()
                .statusCode(SC_OK)
                .body(USUARIO_NOME_PRIMEIRO, Matchers.is(NOME_USUARIO_AP))
                .body(USUARIO_EMAIL_PRIMEIRO, Matchers.is(email))
                .body(USUARIO_PSWD_PRIMEIRO, Matchers.is(PSWD))
                .body(USUARIO_ADMIN_PRIMEIRO, Matchers.is(TRUE))
                .body(USUARIO_ID_PRIMEIRO, Matchers.is(useID))
        ;

        ObjetosUsuarios objetosUsuariosUpdate = new ObjetosUsuarios();
        objetosUsuariosUpdate.setAdministrador(FALSE);
        objetosUsuariosUpdate.setEmail(ALTERADO + email);
        objetosUsuariosUpdate.setNome(ALTERADO + NOME_JA);
        objetosUsuariosUpdate.setPassword(PSWD);

        usuariosServerRest.putUsuario(useID, objetosUsuariosUpdate)
                .log().all()
                .statusCode(SC_OK)
                .body(MESSAGE, Matchers.is(REGISTRO_ALTERADO))
        ;

        usuariosServerRest.getUsuarioPathId(useID)
                .log().all()
                .body(NOME, Matchers.is(ALTERADO  +NOME_JA))
                .body(ADMINISTRADOR, Matchers.is(FALSE))
                .body(EMAIL, Matchers.is(ALTERADO + email))
                .body(PASSWORD, Matchers.is(PSWD))

        ;

        usuariosServerRest.delUsuario(useID)
                .statusCode(SC_OK)
                .log().all()
                .body(MESSAGE, Matchers.is(REGISTRO_EXCLUIDO))
        ;

        usuariosServerRest.getUsuarioPathId(useID)
                .statusCode(SC_BAD_REQUEST)
                .log().all()
                .body(MESSAGE, Matchers.is(USUARIO_NAO_ENCONTRADO))
        ;
    }
}



