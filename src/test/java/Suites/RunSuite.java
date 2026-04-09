package Suites;

import Usuarios.DeleteUsuario.DeleteUsuario;
import Usuarios.E2E.EndToEnd;
import Usuarios.GetUsuario.GetUsuarios;
import Usuarios.PostUsuarios.PostUsuarios;
import Usuarios.PutUsuario.PutUsuario;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({GetUsuarios.class, PostUsuarios.class, DeleteUsuario.class, PutUsuario.class, EndToEnd.class})
public class RunSuite {

}
