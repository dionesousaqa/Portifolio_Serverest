package E2E;

import core.ObjetosUsuarios;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.UUID;

public class EndToEnd {
    @Test
    public void validandoCadastroUsuario() {
        String uniquePart = UUID.randomUUID().toString(); // Gera uma string única
        String email = "testuser+" + uniquePart + "@example.com";

        ObjetosUsuarios objetosUsuarios = new ObjetosUsuarios();
        objetosUsuarios.setNome("Antonio José");
        objetosUsuarios.setEmail(email);
        objetosUsuarios.setPassword("123Antoni");
        objetosUsuarios.setAdministrador("true");

        String useID = RestAssured.given()
                .body(objetosUsuarios)
                .contentType(ContentType.JSON)
                .when()
                .post("https://serverest.dev/usuarios")
                .then()
                .log().all()
                .statusCode(201)
                .extract().path("_id")


                ;


        RestAssured.given()
                .queryParam("_id", useID)
                .contentType(ContentType.JSON)
                .when()
                .get("https://serverest.dev/usuarios")
                .then()
                .log().all()
                .statusCode(200)
                .body("usuarios.nome[0]", Matchers.is("Antonio José"))
                .body("usuarios.email[0]", Matchers.is(email))
                .body("usuarios.password[0]", Matchers.is("123Antoni"))
                .body("usuarios.administrador[0]", Matchers.is("true"))
                .body("usuarios._id[0]", Matchers.is(useID))
        ;

        ObjetosUsuarios objetosUsuariosUpdate = new ObjetosUsuarios();
            objetosUsuariosUpdate.setAdministrador("false");
            objetosUsuariosUpdate.setEmail("Alterado"+ email);
            objetosUsuariosUpdate.setNome("Alterado "+"José Antonio");
            objetosUsuariosUpdate.setPassword("123J0s3$");

       RestAssured.given()

                .pathParam("_id", useID)
                .body(objetosUsuariosUpdate)
                .contentType(ContentType.JSON)
                .when()
                .put("https://serverest.dev/usuarios/{_id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", Matchers.is("Registro alterado com sucesso"))


                ;

       RestAssured.given()
               .pathParam("_id", useID)
               .contentType(ContentType.JSON)
               .when()
               .get("https://serverest.dev/usuarios/{_id}")
               .then()
               .log().all()
               .body("nome", Matchers.is("Alterado "+"José Antonio"))
               .body("administrador", Matchers.is("false"))
               .body("email" , Matchers.is("Alterado"+ email))
               .body("password",Matchers.is("123J0s3$"))

               ;

        RestAssured.given()
                .pathParam("_id", useID)
                .contentType(ContentType.JSON)
                .when()
                .delete("https://serverest.dev/usuarios/{_id}")
                .then()
                .statusCode(200)
                .log().all()
                .body("message", Matchers.is("Registro excluído com sucesso"))

        ;

        RestAssured.given()
                .pathParam("_id", useID)
                .contentType(ContentType.JSON)
                .when()
                .get("https://serverest.dev/usuarios/{_id}")
                .then()
                .statusCode(400)
                .log().all()
                .body("message", Matchers.is( "Usuário não encontrado"))

                ;

    }

}
//    @Test
//        public void AEIOU(){
//     String id =   RestAssured.given()
//                         .queryParam("_id" , "eW1k7Ite3cS4HMOS")
//                         .contentType(ContentType.JSON)
//                   .when()
//                        .get("https://serverest.dev/usuarios")
//                   .then()
//                       .log().all()
//                       .statusCode(200)
//                       .extract().path("usuarios._id[0]")
//
//                ;
//     System.out.println(id);


