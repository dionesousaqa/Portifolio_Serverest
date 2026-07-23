package Contratos.Usuarios;

import Componentes.Usuarios.ObjetosUsuarios;
import Utils.SchemaPaths;
import core.BaseTest;
import org.junit.jupiter.api.Test;
import static Utils.MetodosUltils.gerarEmailUnico;
import java.io.File;
import static Utils.Utilitarios.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.http.HttpStatus.SC_CREATED;

public class ContratoPost extends BaseTest {
    @Test
    public void ValidacaoContratoPost() {
        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setPassword(PSWD);
        objetosUsuarios.setNome(NOME_JL);
        objetosUsuarios.setAdministrador(TRUE);
        objetosUsuarios.setEmail(gerarEmailUnico());

        File jsonSchema = new File(SchemaPaths.POST_CONTRATO_USURIOS);

        String id = usuariosServerRest.postUsuario(objetosUsuarios)
                .statusCode(SC_CREATED)
                .body(matchesJsonSchema(jsonSchema))
                .extract().path(ID);

        usuariosServerRest.deletUsuario(id);
    }
}

