package Usuarios.GetUsuario;

import core.BaseTest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static Utils.MetodosUltils.gerarEmailUnico;

import static Utils.TestesUtils.postUsuario;
import static Utils.Utilitarios.*;
import static org.apache.http.HttpStatus.SC_OK;


public class GetUsuarios extends BaseTest {

    @Test
    public void devoListarTodosUsuarios() {

        usuariosServerRest.getUsuarioPath()
                .statusCode(SC_OK)
                .body(QUANTIDADE, Matchers.greaterThan(NUMERAL_ZERO))
        ;
    }

    @Test
    public void validarExcecaoAoPassarIdInexistente() {
        usuariosServerRest.getUsuarioQuery(Map.of(ID, ID_INEXISTENTE))
                .statusCode(SC_OK)
                .body(QUANTIDADE, Matchers.is(NUMERAL_ZERO))
        ;
    }

    @Test
    public void deveListarPorIdExistente() {
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        usuariosServerRest.getUsuarioQuery(Map.of(ID, id))
                .statusCode(SC_OK)
                .body(USUARIO_ID_PRIMEIRO, Matchers.is(id))
        ;
        usuariosServerRest.deletUsuario(id);
    }

    @Test
    public void deveValidarNomeDeUsuario() {
        String email = gerarEmailUnico();
        String id = postUsuario(email);

        usuariosServerRest.getUsuarioQuery(Map.of(NOME, NOME_USUARIO_AP))
                .statusCode(SC_OK)
                .body(USUARIOS_NOME, Matchers.everyItem(Matchers.equalTo(NOME_USUARIO_AP)))
        ;
        usuariosServerRest.deletUsuario(id);
    }

    @Test
    public void deveValidarNomeInexistente() {
        usuariosServerRest.getUsuarioQuery(Map.of(NOME, NOME_USUARIO_GA))
                .statusCode(SC_OK)
                .body(QUANTIDADE, Matchers.is(0))
        ;
    }

    @Test
    public void deveValidarEmailExistente() {
        usuariosServerRest.getUsuarioQuery(Map.of(EMAIL, EMAIL_BELTR))
                .statusCode(SC_OK)
                .body(USUARIOS_EMAIL, Matchers.everyItem(Matchers.equalTo(EMAIL_BELTR)))
        ;
    }

    @Test
    public void deveValidarEmailInexistente() {
        usuariosServerRest.getUsuarioQuery(Map.of(EMAIL, EMAIL_TST))
                .statusCode(SC_OK)
                .body(QUANTIDADE, Matchers.is(0))
        ;
    }

    @Test
    public void deveValidarAdministradorTrue() {
        usuariosServerRest.getUsuarioQuery(Map.of(ADMINISTRADOR, TRUE))
                .statusCode(SC_OK)
                .body(USUARIOS_ADMINISTRADOR, Matchers.everyItem(Matchers.equalTo(TRUE)))
        ;
    }

    @Test
    public void deveValidarAdministradorFalse() {
        usuariosServerRest.getUsuarioQuery(Map.of(ADMINISTRADOR, FALSE))
                .statusCode(SC_OK)
                .body(USUARIOS_ADMINISTRADOR, Matchers.everyItem(Matchers.equalTo(FALSE)))
        ;
    }
}