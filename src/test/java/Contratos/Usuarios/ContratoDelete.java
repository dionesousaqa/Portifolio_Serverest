package Contratos.Usuarios;

import Utils.SchemaPaths;
import core.BaseTest;
import org.junit.jupiter.api.Test;
import java.io.File;
import static Utils.MetodosUltils.gerarEmailUnico;
import static Utils.TestesUtils.postUsuario;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class ContratoDelete extends BaseTest {
    @Test
    public void ValidarContratoDelete() {
        String id = postUsuario(gerarEmailUnico());

        File jsonSchema = new File(SchemaPaths.DEL_CONTRATO_USUARIOS);

        usuariosServerRest.delUsuario(id)
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema))
        ;
    }
}
