package Suites;

import DeleteUsuario.DeleteUsuario;
import GetUsuario.GetUsuarios;
import PostUsuarios.PostUsuarios;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GetUsuarios.class, PostUsuarios.class, DeleteUsuario.class})
public class RunSuite {

}
