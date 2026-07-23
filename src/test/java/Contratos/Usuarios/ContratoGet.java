package Contratos.Usuarios;

import Utils.SchemaPaths;
import core.BaseTest;
import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_OK;

public class ContratoGet extends BaseTest {
    @Test
    public void ValidacaoDeContrato() {
        File jsonSchema = new File(SchemaPaths.GET_CONTRATO_USUARIOS);

        usuariosServerRest.getUsuarioPath()
                .statusCode(SC_OK)
                .body(matchesJsonSchema(jsonSchema));
        ;
    }
}
