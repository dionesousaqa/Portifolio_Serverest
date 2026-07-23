package Contratos.Usuarios;

import Componentes.Usuarios.ObjetosUsuarios;
import Utils.SchemaPaths;
import core.BaseTest;
import org.junit.jupiter.api.Test;
import java.io.File;
import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.postUsuario;
import static Utils.Utilitarios.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class ContratoPut extends BaseTest {
    @Test
    public void ValidarContatoPut() {
        String id = postUsuario(gerarEmailUnico());

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setPassword(NUM_SEQUEN + ALTERADO);
        objetosUsuarios.setEmail(ALTERADO + gerarEmailUnico());
        objetosUsuarios.setNome(NOME_JOSE + ALTERADO);
        objetosUsuarios.setAdministrador(FALSE);

        File jsonSchema = new File(SchemaPaths.PUT_CONTRTO_USUARIOS);

        usuariosServerRest.putUsuario(id, objetosUsuarios)
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema))
        ;
        usuariosServerRest.deletUsuario(id);
    }
}
