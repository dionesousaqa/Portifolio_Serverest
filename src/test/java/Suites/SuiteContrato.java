package Suites;

import Contrato.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ContratoPost.class, ContratoGet.class, ContratoPut.class, ContratoDelete.class})
public class SuiteContrato {

}
