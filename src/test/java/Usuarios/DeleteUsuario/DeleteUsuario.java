package Usuarios.DeleteUsuario;

import core.BaseTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static Utils.TestesUtils.deletUsuario;
import static Utils.TestesUtils.postUsuario;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.SC_OK;

public class DeleteUsuario extends BaseTest {
    @Test
    public void deletandoUsuario () {
        String id = postUsuario(EMAIL_ATP);

       usuariosServerRest.delUsuario(id)
                      .statusCode(SC_OK).log().all()
                      .body(MESSAGE, Matchers.is(REGISTRO_EXCLUIDO))
                ;
        usuariosServerRest.deletUsuario(id);
    }
    @Test
    public void deletandoUsuarioComIdInexistente () {

        usuariosServerRest.delUsuario(ID_INEXISTENTE)
                   .statusCode(SC_OK).log().all()
                   .body(MESSAGE, Matchers.is(NENHUM_REGISTRO_EXCLUIDO))
        ;
    }
}
