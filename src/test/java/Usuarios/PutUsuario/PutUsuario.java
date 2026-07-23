package Usuarios.PutUsuario;

import core.BaseTest;
import Componentes.Usuarios.ObjetosUsuarios;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static Utils.MetodosUltils.gerarEmailUnico;

import static Utils.TestesUtils.postUsuario;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.SC_OK;

public class PutUsuario extends BaseTest {
    @Test
    public void devoAtualizarNomedeUsuario() {
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setNome(NOME_ALEATORIO);
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.putUsuario(id, objetosUsuarios)
                .statusCode(SC_OK).log().all()
                .extract()
                .response()
        ;
        usuariosServerRest.deletUsuario(id);
    }

    @Test
    public void deveAlterarAdministrador() {
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(FALSE);
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setNome(NOME_ALEATORIO);
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.putUsuario(id, objetosUsuarios)
                .statusCode(SC_OK).log().all()
                .body(MESSAGE, Matchers.is((REGISTRO_ALTERADO)))

        ;
        usuariosServerRest.deletUsuario(id);
    }

    @Test
    public void devoAlterarPassword() {
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setAdministrador(FALSE);
        objetosUsuarios.setNome(NOME_ALEATORIO);
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setPassword(PSWD);

        usuariosServerRest.putUsuario(id, objetosUsuarios)
                .statusCode(SC_OK).log().all()
                .body(MESSAGE, Matchers.is(REGISTRO_ALTERADO))
        ;
        usuariosServerRest.deletUsuario(id);
    }

    @Test
    public void devoAlterarEmail() {
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setPassword(PSWD);
        objetosUsuarios.setEmail(EMAIL_ATT);
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setNome(NOME_ALEATORIO);

        usuariosServerRest.putUsuario(id, objetosUsuarios)
                .statusCode(SC_OK).log().all()
                .body(MESSAGE, Matchers.is(REGISTRO_ALTERADO))
        ;
        usuariosServerRest.deletUsuario(id);
    }

}
