package Suites;

import GetUsuario.GetUsuarios;
import PostUsuarios.PostUsuarios;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GetUsuarios.class, PostUsuarios.class})
public class RunSuite {

}
