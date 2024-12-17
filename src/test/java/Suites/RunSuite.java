package Suites;

import DeleteUsuario.DeleteUsuario;
import E2E.EndToEnd;
import GetUsuario.GetUsuarios;
import PostUsuarios.PostUsuarios;
import PutUsuario.PutUsuario;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GetUsuarios.class, PostUsuarios.class, DeleteUsuario.class, PutUsuario.class, EndToEnd.class})
public class RunSuite {

}
